package edu.sjtu.SCEPHelper.db.models;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-22
 * Time: 下午9:09
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "paper_records")
public class PaperRecord implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private int totalPoint;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Paper paper;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private User user;

    @ForeignCollectionField
    private ForeignCollection<Answer> answers;

    PaperRecord(){

    }

    public PaperRecord(Paper paper, User user) {
        this.paper = paper;
        this.user = user;
    }

    private int calcTotalPoint(){
        CloseableIterator<Answer> iterator = answers.closeableIterator();
        totalPoint = 0 ;
        while(iterator.hasNext()){
            Answer answer = iterator.next();
            totalPoint += answer.getComment().getGainPoint();
        }
        try{
            iterator.close();
        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return totalPoint;
    }

    public int getId() {
        return id;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public Paper getPaper() {
        return paper;
    }

    public User getUser() {
        return user;
    }

    public ForeignCollection<Answer> getAnswers() {
        return answers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAnswers(ForeignCollection<Answer> answers) {
        this.answers = answers;
    }
}
