package edu.sjtu.SCEP.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-22
 * Time: 下午10:11
 * To change this template use File | Settings | File Templates.
 */


@DatabaseTable(tableName = "choices")
public class Choice {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField()
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
}
