package edu.sjtu.ETHelper.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-22
 * Time: 下午9:10
 * To change this template use File | Settings | File Templates.
 */


@DatabaseTable(tableName = "users")
public class User implements Serializable {
    public enum Group{HeadTeacher, Teachers, Students}

    @DatabaseField(canBeNull = false, id = true)
    private String username;

    @DatabaseField(canBeNull = false)
    private int hashPass;

    @DatabaseField(canBeNull = false)
    private String name = "";


    @DatabaseField(canBeNull = false)
    private Group group;

    User() {

    }

    public User(String username, int hashPass, Group group) {
        this.username = username;
        this.hashPass = hashPass;
        this.group = group;
    }

    public User(String username, int hashPass, String name, Group group) {
        this.username = username;
        this.hashPass = hashPass;
        this.name = name;
        this.group = group;
    }

    public String getUsername() {
        return username;
    }

    public int getHashPass() {
        return hashPass;
    }

    public String getName() {
        return name;
    }

    public Group getGroup() {
        return group;
    }
}
