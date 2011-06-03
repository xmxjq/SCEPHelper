package edu.sjtu.SCEPHelper.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-22
 * Time: 下午10:11
 * To change this template use File | Settings | File Templates.
 */


@DatabaseTable(tableName = "choices")
public class Choice implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(index = true)
    private int nr;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Question question;

    Choice(){

    }

    public Choice(String name, int nr, Question question){
        this.name = name;
        this.nr = nr;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNr() {
        return nr;
    }

    public Question getQuestion() {
        return question;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
