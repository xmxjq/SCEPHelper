package edu.sjtu.SCEPHelper.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-22
 * Time: 下午9:33
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "comments")
public class Comment implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private boolean comment;

    @DatabaseField(canBeNull = false)
    private int gainPoint;

    Comment(){

    }

    public Comment(boolean comment) {
        this.comment = comment;
    }

    public Comment(boolean comment, int gainPoint) {
        this.comment = comment;
        this.gainPoint = gainPoint;
    }

    public int calcGainPoint(Answer answer){
        if (comment){
            this.gainPoint = answer.getQuestion().getPoint();
        } else{
            this.gainPoint = 0;
        }
        return gainPoint;
    }

    public int getId() {
        return id;
    }

    public boolean getComment() {
        return comment;
    }

    public int getGainPoint() {
        return gainPoint;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public void setGainPoint(int gainPoint) {
        this.gainPoint = gainPoint;
    }
}
