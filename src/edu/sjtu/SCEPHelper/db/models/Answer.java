package edu.sjtu.SCEPHelper.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.DatabaseTable;
import edu.sjtu.SCEPHelper.db.DBHelper;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-22
 * Time: 下午9:10
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "answers")
public class Answer implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String answer;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Question question;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private PaperRecord paperRecord;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Comment comment;

    Answer(){
    }

    public Answer(String answer, Question question, PaperRecord paperRecord, Comment comment){
        this.answer = answer;
        this.question = question;
        this.paperRecord = paperRecord;
        this.comment = comment;
        comment.calcGainPoint(this);
    }

    public Answer(String answer, Question question, PaperRecord paperRecord) {
        this.answer = answer;
        this.question = question;
        this.paperRecord = paperRecord;
    }

    public static ArrayList<Answer> queryByPaperRecord(PaperRecord paperRecord) throws Exception{
        QueryBuilder<Answer, Integer> queryBuilder = DBHelper.getDbHelper().getAnswerIntegerDao().queryBuilder();
        Where<Answer, Integer> where = queryBuilder.where();
        SelectArg selectArg = new SelectArg();
        where.eq("paperRecord_id", selectArg);
        PreparedQuery<Answer> preparedQuery = queryBuilder.prepare();

        selectArg.setValue(paperRecord.getId());
        return (ArrayList<Answer>)DBHelper.getDbHelper().getAnswerIntegerDao().query(preparedQuery);
    }

    public String[] toAnswerStrings(){
        ArrayList<String> answers = new ArrayList<String>();
        if (question.getQuestionType()==Question.QuestionType.MultipleChoices||
                question.getQuestionType()==Question.QuestionType.SingleChoice){
            try{
                ArrayList<Choice> choices = question.getSerializableChoices();
                String[] nrs = answer.split(" ");
                for(Choice choice: choices){
                    for(String nr: nrs){
                        if(nr.equals(Integer.toString(choice.getNr()))){
                            answers.add(choice.getName());
                            break;
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            answers.add(answer);
        }
        return answers.toArray(new String[answers.size()]);
    }

    public int getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public Question getQuestion() {
        return question;
    }

    public PaperRecord getPaperRecord() {
        //return paperRecord;
        return null;
    }

    public Comment getComment() {
        return comment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setPaperRecord(PaperRecord paperRecord) {
        this.paperRecord = paperRecord;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }


}
