package edu.sjtu.SCEPHelper.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-6-5
 * Time: 下午8:11
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "correct_answers")
public class CorrectAnswer implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String answer;

    public CorrectAnswer() {
    }

    public CorrectAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
