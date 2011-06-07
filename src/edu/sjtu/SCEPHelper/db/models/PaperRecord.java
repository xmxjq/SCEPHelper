package edu.sjtu.SCEPHelper.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.DatabaseTable;
import edu.sjtu.SCEPHelper.db.DBHelper;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

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

    public ArrayList<Answer> serializableAnswers = new ArrayList<Answer>();

    PaperRecord(){

    }

    public PaperRecord(Paper paper, User user) {
        this.paper = paper;
        this.user = user;
    }

    private int calcTotalPoint(){
        Iterator<Answer> iterator = serializableAnswers.iterator();
        totalPoint = 0 ;
        while(iterator.hasNext()){
            Answer answer = iterator.next();
            totalPoint += answer.getComment().getGainPoint();
        }
        return totalPoint;
    }

    public static ArrayList<PaperRecord> queryByPaper(Paper paper) throws Exception{
        QueryBuilder<PaperRecord, Integer> queryBuilder = DBHelper.getDbHelper().getPaperRecordIntegerDao().queryBuilder();
        Where<PaperRecord, Integer> where = queryBuilder.where();
        SelectArg selectArg = new SelectArg();
        where.eq("paper_id", selectArg);
        PreparedQuery<PaperRecord> preparedQuery = queryBuilder.prepare();

        selectArg.setValue(paper.getId());
        return (ArrayList<PaperRecord>)DBHelper.getDbHelper().getPaperRecordIntegerDao().query(preparedQuery);
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

    public ArrayList<Answer> getSerializableAnswers() {
        return serializableAnswers;
    }

    public void setSerializableAnswers(ArrayList<Answer> serializableAnswers) {
        this.serializableAnswers = serializableAnswers;
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

}
