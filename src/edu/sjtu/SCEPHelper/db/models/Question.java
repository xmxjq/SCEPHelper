package edu.sjtu.SCEPHelper.db.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.DatabaseTable;
import edu.sjtu.SCEPHelper.db.DBHelper;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import sun.jvm.hotspot.code.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-22
 * Time: 下午9:08
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "questions")
public class Question implements Serializable {
    public enum QuestionType{SingleChoice,
                             MultipleChoices,
                             SingleLineInput,
                             MultipleLineInput}

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private int point;

    @DatabaseField(canBeNull = false)
    private QuestionType questionType;

    @DatabaseField(canBeNull = false)
    private int nr;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Category category;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private CorrectAnswer correctAnswer;

    //@JsonIgnore
   // @ForeignCollectionField
   // private ForeignCollection<Choice> choices;

    private ArrayList<Choice> serializableChoices = new ArrayList<Choice>();

    public int getNr() {
        return nr;
    }

    Question(){

    }

    public Question(String name,
                    int point,
                    int nr,
                    QuestionType questionType,
                    Category category,
                    CorrectAnswer correctAnswer) throws Exception{
        if (category.getCategoryType()!= Category.CategoryType.QuestionType){
            throw new Exception("分类类型不匹配");
        }
        this.name = name;
        this.point = point;
        this.nr = nr;
        this.questionType = questionType;
        this.category = category;
        this.correctAnswer = correctAnswer;
    }

    public static List<Question> queryByCategory(Category category) throws Exception{
        QueryBuilder<Question, Integer> queryBuilder = DBHelper.getDbHelper().getQuestionIntegerDao().queryBuilder();
        Where<Question, Integer> where = queryBuilder.where();
        SelectArg selectArg = new SelectArg();
        where.eq("category_id", selectArg);
        PreparedQuery<Question> preparedQuery = queryBuilder.prepare();

        selectArg.setValue(category.getId());
        return DBHelper.getDbHelper().getQuestionIntegerDao().query(preparedQuery);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public Category getCategory() {
        return null;
    }

    //public ForeignCollection<Choice> getChoices() {
    //    return choices;
    //}

    public ArrayList<Choice> getSerializableChoices() {
        return serializableChoices;
    }

    public CorrectAnswer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(CorrectAnswer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setSerializableChoices(ArrayList<Choice> serializableChoices) {
        this.serializableChoices = serializableChoices;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
