package edu.sjtu.ETHelper.db.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-22
 * Time: 下午9:08
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "questions")
public class Question {
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

    @ForeignCollectionField
    private ForeignCollection<Choice> choices;

    public int getNr() {
        return nr;
    }

    Question(){

    }

    public Question(String name, int point, int nr, QuestionType questionType, Category category)
            throws Exception{
        if (category.getCategoryType()!= Category.CategoryType.QuestionType){
            throw new Exception("分类类型不匹配");
        }
        this.name = name;
        this.point = point;
        this.nr = nr;
        this.questionType = questionType;
        this.category = category;
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
        return category;
    }

    public ForeignCollection<Choice> getChoices() {
        return choices;
    }
}
