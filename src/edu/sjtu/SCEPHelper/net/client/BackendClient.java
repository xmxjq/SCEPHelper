package edu.sjtu.SCEPHelper.net.client;

import edu.sjtu.SCEPHelper.db.models.*;
import edu.sjtu.SCEPHelper.net.*;
import org.restlet.data.Cookie;
import org.restlet.data.CookieSetting;
import org.restlet.data.Form;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.util.Series;

import java.util.ArrayList;


/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-28
 * Time: 下午7:27
 * To change this template use File | Settings | File Templates.
 */
public class BackendClient {
    private static User currentUser = null;
    private static Series<Cookie> cookies = null;
    private static LoginResource loginResource = null;
    private static ClientResource loginClientResource = null;
    private String baseURL = null;

    private ArrayList<ClientResource> clientResources = new ArrayList<ClientResource>(); // 连接池

    public BackendClient(String url, int port) {
        this.baseURL = String.format("http://%s:%d", url.replaceFirst("http://", ""), port);
    }

    public UserResource getUserResource(String username) {
        ClientResource cr = new ClientResource(baseURL+ ResourceURL.USER_URL.replace("{username}", username));
        cr.setCookies(cookies);
        clientResources.add(cr);
        return cr.wrap(UserResource.class);
    }

    private LoginResource getLoginResource() {
        ClientResource cr = new ClientResource(baseURL + ResourceURL.LOGIN_URL);
        loginClientResource = cr;
        clientResources.add(cr);
        return cr.wrap(LoginResource.class);
    }

    public boolean isLogin(){
        return (currentUser!=null);
    }

    public User login(String username, String password) throws ResourceException {
        if(isLogin()) return currentUser;
        if(loginResource==null) loginResource = getLoginResource();

        Form form = new Form();
        form.add("username", username);
        form.add("password", password);
        currentUser = loginResource.login(form);

        Series<CookieSetting> cookieSettings = loginClientResource.getResponse().getCookieSettings();
        String cookie = cookieSettings.getFirstValue("requestUser", null);
        cookies = loginClientResource.getRequest().getCookies();
        cookies.add("requestUser", cookie);

        loginClientResource.release();

        return currentUser;
    }

    public void logout() throws ResourceException{
        if(isLogin()){
            currentUser = null;
            cookies = null;
            loginClientResource = null;
            loginResource = null;
        }
    }

    public PaperResource getPaperResource() {
        ClientResource cr = new ClientResource(baseURL + ResourceURL.PAPER_URL);
        cr.setCookies(cookies);
        clientResources.add(cr);
        return cr.wrap(PaperResource.class);
    }

    public PaperResource getPaperResource(int paper_id) {
        ClientResource cr = new ClientResource(baseURL + ResourceURL.PAPER_URL);
        cr.setCookies(cookies);
        cr.getReference().addQueryParameter("paper_id", Integer.toString(paper_id));
        clientResources.add(cr);
        return cr.wrap(PaperResource.class);
    }

    public RecordResource getRecordResource(){
        ClientResource cr = new ClientResource(baseURL + ResourceURL.RECORD_URL);
        cr.setCookies(cookies);
        clientResources.add(cr);
        return cr.wrap(RecordResource.class);
    }

    public RecordResource getRecordResource(int record_id){
        ClientResource cr = new ClientResource(baseURL + ResourceURL.RECORD_URL);
        cr.setCookies(cookies);
        cr.getReference().addQueryParameter("record_id", Integer.toString(record_id));
        clientResources.add(cr);
        return cr.wrap(RecordResource.class);
    }

    public boolean testConnection() {
        ClientResource cr = new ClientResource(baseURL);
        String response = cr.get().toString();
        cr.release();
        if(response!=null){
            System.out.println(response);
            return true;
        }
        return false;
    }

    public void releaseAll(){
        // 释放连接池
        for(ClientResource clientResource: clientResources){
            clientResource.release();
        }
    }

    public static void main(String args[]) {
        BackendClient bc = new BackendClient("127.0.0.1", 8182);
        try{
            //
            // 测试登陆资源
            //
            User cu = bc.login("root", "root"); // 登陆
            System.out.println("[LOGIN]: "+ cu.getUsername());

            //
            // 测试用户资源
            //
            UserResource userResource = bc.getUserResource("student"); // 获得连接
            User student = userResource.retrieve(); // 获得用户
            System.out.println(String.format("[USER]: %s, %s, %s",
                    student.getUsername(), student.getName(), student.getGroup().toString()));

            student.setName("修改学生");
            userResource.store(student); // 修改用户

            student = userResource.retrieve();
            System.out.println(String.format("[USER_MODIFIED]: %s, %s, %s",
                                student.getUsername(), student.getName(), student.getGroup().toString()));
            bc.releaseAll(); // 关闭连接

            //
            // 测试试卷资源
            //
            PaperResource paperResource = bc.getPaperResource(); // 获得连接
            Paper paper = new Paper("测试试卷");


            Category categoryOne = new Category("阅读题", "这是一篇文章", Category.CategoryType.QuestionType, 1, paper);
            Category categoryTwo = new Category("阅读理解", "这是一个分割", Category.CategoryType.SplitType, 2, paper);
            CorrectAnswer correctAnswer = new CorrectAnswer("1");

            Question questionOne = new Question("问题1", 2, 1,
                    Question.QuestionType.SingleChoice, categoryOne, correctAnswer);
            Question questionTwo = new Question("问题2", 2, 2,
                    Question.QuestionType.MultipleChoices, categoryOne, correctAnswer);
            Question questionThree = new Question("问题3", 2, 3,
                    Question.QuestionType.SingleLineInput, categoryOne, correctAnswer);
            Question questionFour = new Question("问题4", 2, 4,
                    Question.QuestionType.MultipleLineInput, categoryOne, correctAnswer);

            Choice choiceOne = new Choice("选项1", 1, questionOne);
            Choice choiceTwo = new Choice("选项1", 1, questionTwo);
            Choice choiceThree = new Choice("选项2", 2, questionTwo);

            paper.getSerializableCategories().add(categoryOne);
            paper.getSerializableCategories().add(categoryTwo);

            categoryOne.getSerializableQuestions().add(questionOne);
            categoryOne.getSerializableQuestions().add(questionTwo);
            categoryOne.getSerializableQuestions().add(questionThree);
            categoryOne.getSerializableQuestions().add(questionFour);

            questionOne.getSerializableChoices().add(choiceOne);
            questionTwo.getSerializableChoices().add(choiceTwo);
            questionTwo.getSerializableChoices().add(choiceThree);

            paperResource.create(paper); // 创建试卷
            bc.releaseAll();
            System.out.println("Created a paper.");

            paperResource = bc.getPaperResource(1);
            paper = paperResource.retrieve(); // 抓取试卷
            System.out.println(String.format("[PAPER]: %s", paper.getName()));
            ArrayList<Category> categories = paper.getSerializableCategories();
            System.out.println(String.format("[Categories]: %s, %s",
                    categories.get(0).getName(),
                    categories.get(1).getName()));

            categories.get(0).setName("修改的阅读理解");   // 注意，这里 categoryOne 已经不是 paper 的属性了
            paperResource.update(paper); // 更新问卷
            paper = paperResource.retrieve();
            bc.releaseAll(); // 关闭连接

            System.out.println(String.format("[PAPER_MODIFIED]: %s",
                    paper.getSerializableCategories().get(0).getName()));

            //
            // 测试答卷资源
            //
            PaperRecord paperRecord = new PaperRecord(paper, student);

            Answer answerOne = new Answer("1", questionOne, paperRecord); // 单选
            Answer answerTwo = new Answer("1 2", questionTwo, paperRecord); // 多选
            paperRecord.getSerializableAnswers().add(answerOne);
            paperRecord.getSerializableAnswers().add(answerTwo);

            bc.logout(); // 登出
            bc.login("student", "student"); //登陆为学生
            RecordResource recordResource = bc.getRecordResource(); // 获得连接
            recordResource.submit(paperRecord); // 提交答卷
            bc.releaseAll(); // 关闭连接
            System.out.println("Created a Record.");

            bc.logout();
            bc.login("teacher", "teacher"); //登陆为老师
            recordResource = bc.getRecordResource(1);
            paperRecord = recordResource.retrieve();
            System.out.println("[RECORD]: " + paperRecord.getId() + " " + paperRecord.getPaper().getName());

            ArrayList<Answer> answers = paperRecord.getSerializableAnswers();
            System.out.println(String.format("[ANSWERS]: %s, %s",
                    answers.get(0).getAnswer(),
                    answers.get(1).getAnswer()));
            String[] answerChoice = answers.get(0).toAnswerStrings();
            System.out.println(String.format("[CHOICE]: %s", answerChoice.toString()));

            Comment commentOne = new Comment(true);
            Comment commentTwo = new Comment(false, 1);
            answers.get(0).setComment(commentOne);
            answers.get(1).setComment(commentTwo);

            recordResource.correction(paperRecord); // 提交批改
            paperRecord = recordResource.retrieve();
            answers = paperRecord.getSerializableAnswers();
            System.out.println(String.format("[CORRECTION]: %s, %s",
                    answers.get(0).getComment().getComment(),
                    answers.get(1).getComment().getComment()));

            answers.get(0).getComment().setComment(false);
            recordResource.correction(paperRecord); // 修改批改
            paperRecord = recordResource.retrieve();
            answers = paperRecord.getSerializableAnswers();
            System.out.println(String.format("[CORRECTION_MODIFIED]: %s, %s",
                    answers.get(0).getComment().getComment(),
                    answers.get(1).getComment().getComment()));
            bc.releaseAll(); // 关闭连接

            // 登出操作
            bc.logout();

        }catch (ResourceException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
