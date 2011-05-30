package edu.sjtu.SCEPHelper.db.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-22
 * Time: 下午9:04
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "papers")
public class Paper {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @ForeignCollectionField
    private ForeignCollection<Category> categories;

    Paper(){
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    public Paper(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ForeignCollection<Category> getCategories() {
        return categories;
    }
}
