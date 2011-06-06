package edu.sjtu.SCEPHelper.db.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import org.codehaus.jackson.annotate.JacksonAnnotation;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-22
 * Time: 下午9:04
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "papers")
public class Paper implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    //@JsonIgnore
    //@ForeignCollectionField
    //private ForeignCollection<Category> categories;

    private ArrayList<Category> serializableCategories = new ArrayList<Category>();

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

    //public ForeignCollection<Category> getCategories() {
    //    return categories;
    //}

    public ArrayList<Category> getSerializableCategories() {
        return serializableCategories;
    }

    public void setSerializableCategories(ArrayList<Category> serializableCategories) {
        this.serializableCategories = serializableCategories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    //public void setCategories(ForeignCollection<Category> categories) {
    //    this.categories = categories;
    //}
}
