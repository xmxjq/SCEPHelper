package edu.sjtu.ETHelper.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-22
 * Time: 下午9:10
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "answers")
public class Answer {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String answer;

    @DatabaseField(foreign = true)
    private Question question;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private PaperRecord paperRecord;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Comment comment;

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
        return paperRecord;
    }

    public Comment getComment() {
        return comment;
    }

    Answer(){

    }

    public Answer(String answer, Question question, PaperRecord paperRecord, Comment comment){
        this.answer = answer;
        this.question = question;
        this.paperRecord = paperRecord;
        this.comment = comment;
        comment.calcGainPoint(this);
    }
}
