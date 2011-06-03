package edu.sjtu.SCEPHelper.net.client;

import edu.sjtu.SCEPHelper.db.models.*;
import edu.sjtu.SCEPHelper.net.*;
import org.restlet.data.Cookie;
import org.restlet.data.CookieSetting;
import org.restlet.data.Form;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.util.Series;


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

    public BackendClient(String url, int port) {
        this.baseURL = String.format("http://%s:%d", url.replaceFirst("http://", ""), port);
    }

    public UserResource getUserResource(String username) {
        ClientResource cr = new ClientResource(baseURL+ ResourceURL.USER_URL.replace("{username}", username));
        cr.setCookies(cookies);
        return cr.wrap(UserResource.class);
    }

    private LoginResource getLoginResource() {
        ClientResource cr = new ClientResource(baseURL + ResourceURL.LOGIN_URL);
        loginClientResource = cr;
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
        return cr.wrap(PaperResource.class);
    }

    public PaperResource getPaperResource(int paper_id) {
        ClientResource cr = new ClientResource(baseURL + ResourceURL.PAPER_URL);
        cr.setCookies(cookies);
        cr.getReference().addQueryParameter("paper_id", Integer.toString(paper_id));
        return cr.wrap(PaperResource.class);
    }

    public RecordResource getRecordResource(){
        ClientResource cr = new ClientResource(baseURL + ResourceURL.PAPER_URL);
        cr.setCookies(cookies);
        return cr.wrap(RecordResource.class);
    }

    public RecordResource getRecordResource(int record_id){
        ClientResource cr = new ClientResource(baseURL + ResourceURL.PAPER_URL);
        cr.setCookies(cookies);
        cr.getReference().addQueryParameter("record_id", Integer.toString(record_id));
        return cr.wrap(RecordResource.class);
    }

    public boolean testConnection() {
        ClientResource cr = new ClientResource(baseURL);
        String response = cr.get().toString();
        if(response!=null){
            System.out.println(response);
            return true;
        }
        return false;
    }

    public static void main(String args[]) {
        BackendClient bc = new BackendClient("127.0.0.1", 8182);
        try{
            User cu = bc.login("root", "root");
            System.out.println("[LOGIN]: "+ cu.getUsername());

            UserResource userResource = bc.getUserResource("student");
            User student = userResource.retrieve();
            System.out.println(String.format("[USER]: %s, %s, %s",
                    student.getUsername(), student.getName(), student.getGroup().toString()));

            student.setName("修改学生");
            userResource.store(student);

            student = userResource.retrieve();
            System.out.println(String.format("[USER_MODIFIED]: %s, %s, %s",
                                student.getUsername(), student.getName(), student.getGroup().toString()));


            PaperResource paperResource = bc.getPaperResource();
            Paper paper = new Paper("测试试卷");
            Category categoryOne = new Category("阅读题", "这是一篇文章", Category.CategoryType.QuestionType, 1, paper);
            Category categoryTwo = new Category("阅读理解", "这是一个分割", Category.CategoryType.SplitType, 2, paper);

            Question questionOne = new Question("问题1", 2, 1, Question.QuestionType.SingleChoice, categoryOne);
            Question questionTwo = new Question("问题2", 2, 2, Question.QuestionType.MultipleChoices, categoryOne);
            Question questionThree = new Question("问题3", 2, 3, Question.QuestionType.SingleLineInput, categoryOne);
            Question questionFour = new Question("问题4", 2, 4, Question.QuestionType.MultipleLineInput, categoryOne);

            Choice choiceOne = new Choice("选项1", 1, questionOne);
            Choice choiceTwo = new Choice("选项1", 1, questionTwo);
            Choice choiceThree = new Choice("选项2", 2, questionTwo);

            paper.getCategories().add(categoryOne);
            paper.getCategories().add(categoryTwo);

            categoryOne.getQuestions().add(questionOne);
            categoryOne.getQuestions().add(questionTwo);
            categoryOne.getQuestions().add(questionThree);
            categoryOne.getQuestions().add(questionFour);

            questionOne.getChoices().add(choiceOne);
            questionTwo.getChoices().add(choiceTwo);
            questionTwo.getChoices().add(choiceThree);

            paperResource.create(paper);

            paperResource = bc.getPaperResource(1);
            paper = paperResource.retrieve();
            System.out.println(String.format("[PAPER]: %s", paper));


        }catch (ResourceException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
