package edu.sjtu.SCEP.db.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.sun.tools.javac.util.Name;
import org.w3c.dom.NameList;

import javax.management.Descriptor;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-22
 * Time: 下午9:08
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "question_types")
public class Category {
    public enum CategoryType{QuestionType, SplitType}

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = true)
    private String description = null;

    @DatabaseField(canBeNull = false)
    private CategoryType categoryType;

    @DatabaseField()
    private int nr;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Paper paper;

    @ForeignCollectionField
    private ForeignCollection<Question> questions;

    Category(){

    }
    public Category(String name, int nr, CategoryType categoryType, Paper paper){
        this.name = name;
        this.nr = nr;
        this.categoryType = categoryType;
        this.paper = paper;
    }

    public Category(String name, String description, CategoryType categoryType, int nr, Paper paper) {
        this.name = name;
        this.description = description;
        this.categoryType = categoryType;
        this.nr = nr;
        this.paper = paper;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public int getNr() {
        return nr;
    }

    public Paper getPaper() {
        return paper;
    }

    public ForeignCollection<Question> getQuestions() {
        return questions;
    }
}
