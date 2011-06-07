package edu.sjtu.SCEPHelper.net.server;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.sun.jdi.connect.Connector;
import edu.sjtu.SCEPHelper.db.DBHelper;
import edu.sjtu.SCEPHelper.db.models.*;
import edu.sjtu.SCEPHelper.net.PaperResource;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-6-3
 * Time: 上午10:22
 * To change this template use File | Settings | File Templates.
 */
public class PaperServerResource extends LoginRequiredResource implements PaperResource{
    private Paper paper = null;

    public PaperServerResource() throws Exception {
        super();
    }

    @Override
    public void doInit() throws ResourceException {
        super.doInit();
        String paperStringId = getRequest().getResourceRef().getQueryAsForm().getFirstValue("paper_id");
        if (paperStringId!=null && !paperStringId.equals("")){
            try{
                paper = DBHelper.getDbHelper().getPaperIntegerDao().queryForId(Integer.parseInt(paperStringId));
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    public Paper retrieve() {
        if(paper!=null)
            retrieveCategories(paper);
        return paper;
    }

    private void retrieveCategories(Paper paper){
        try{
            List<Category> categories = Category.queryByPaper(paper);
            for(Category category: categories){
                retrieveQuestions(category);
            }
            paper.setSerializableCategories((ArrayList<Category>)categories);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void retrieveQuestions(Category category){
        try{
            ArrayList<Question> questions = (ArrayList<Question>)Question.queryByCategory(category);
            for(Question question: questions){
                retrieveChoices(question);
            }
            category.setSerializableQuestions(questions);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void retrieveChoices(Question question){
        try{
            ArrayList<Choice> choices = (ArrayList<Choice>)Choice.queryByQuestion(question);
            question.setSerializableChoices(choices);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void create(Paper paper){
        hardCheckPermission(User.Group.HeadTeacher);
        try{
            DBHelper.getDbHelper().getPaperIntegerDao().create(paper);
            createCategories(paper.getSerializableCategories(), paper);

        }catch (Exception e){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e);
        }
    }

    private void createCategories(ArrayList<Category> categories, Paper paper) throws Exception{
        for(Category category: categories){
            category.setPaper(paper);
            DBHelper.getDbHelper().getCategoryIntegerDao().create(category);

            createQuestions(category.getSerializableQuestions(), category);
        }
    }

    private void createQuestions(ArrayList<Question> questions, Category category) throws Exception{
        for(Question question: questions){
            DBHelper.getDbHelper().getCorrectAnswerIntegerDao().create(question.getCorrectAnswer());
            question.setCategory(category);
            DBHelper.getDbHelper().getQuestionIntegerDao().create(question);

            createChoices(question.getSerializableChoices(), question);
        }
    }

    private void createChoices(ArrayList<Choice> choices, Question question) throws Exception{
        for(Choice choice: choices){
            choice.setQuestion(question);
            DBHelper.getDbHelper().getChoiceIntegerDao().create(choice);
        }
    }


    public void update(Paper paper){
        hardCheckPermission(User.Group.HeadTeacher);
        try{
            updateCategories(paper.getSerializableCategories(), paper);
            DBHelper.getDbHelper().getPaperIntegerDao().update(paper);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateCategories(ArrayList<Category> categories, Paper paper) throws Exception{
        for(Category category: categories){
            updateQuestions(category.getSerializableQuestions(), category);
            category.setPaper(paper);
            DBHelper.getDbHelper().getCategoryIntegerDao().update(category);
        }
    }

    private void updateQuestions(ArrayList<Question> questions, Category category) throws Exception{
        for(Question question: questions){
            updateChoices(question.getSerializableChoices(), question);
            DBHelper.getDbHelper().getCorrectAnswerIntegerDao().update(question.getCorrectAnswer());
            question.setCategory(category);
            DBHelper.getDbHelper().getQuestionIntegerDao().update(question);
        }
    }

    private void updateChoices(ArrayList<Choice> choices, Question question) throws Exception{
        for(Choice choice: choices){
            choice.setQuestion(question);
            DBHelper.getDbHelper().getChoiceIntegerDao().update(choice);
        }
    }

    public void remove() {
        hardCheckPermission(User.Group.HeadTeacher);
        try{
            if (paper!=null)
                DBHelper.getDbHelper().getPaperIntegerDao().delete(paper);
        }catch (Exception e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e);
        }
    }

}
