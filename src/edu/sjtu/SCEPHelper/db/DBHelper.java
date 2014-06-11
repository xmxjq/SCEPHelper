package edu.sjtu.SCEPHelper.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import edu.sjtu.SCEPHelper.db.models.*;
import org.h2.table.Table;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-23
 * Time: ??4:57
 * To change this template use File | Settings | File Templates.
 * Test updated from XJQ 2014.06.11
 */
public class DBHelper {
    private final static String DATABASE_URL = "jdbc:h2:mem:SCEPHelper";
    private static DBHelper dbHelper = null;

    // ????Dao
    private Dao<Paper, Integer> paperIntegerDao;
	private Dao<Category, Integer> categoryIntegerDao;
    private Dao<Question, Integer> questionIntegerDao;
    private Dao<Choice, Integer> choiceIntegerDao;
    private Dao<CorrectAnswer, Integer> correctAnswerIntegerDao;

    private Dao<PaperRecord, Integer> paperRecordIntegerDao;
    private Dao<Answer, Integer> answerIntegerDao;
    private Dao<Comment, Integer> commentIntegerDao;

    private Dao<User, String> userStringDao;

    private JdbcConnectionSource connectionSource = null;


    public DBHelper() throws Exception {
        connectionSource = new JdbcConnectionSource(DATABASE_URL);
		// ???????Dao
		setupDatabase(connectionSource);
    }

    public static DBHelper getDbHelper() throws Exception{
        if(dbHelper==null){
            dbHelper = new DBHelper();
        }
        return dbHelper;
    }

    public static void close(){
        try{
            if (getDbHelper().connectionSource != null) {
		        getDbHelper().connectionSource.close();
            }
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    private void setupDatabase(ConnectionSource connectionSource) throws Exception {
        // ?????Dao
        paperIntegerDao = DaoManager.createDao(connectionSource, Paper.class);
        categoryIntegerDao = DaoManager.createDao(connectionSource, Category.class);
        questionIntegerDao = DaoManager.createDao(connectionSource, Question.class);
        choiceIntegerDao = DaoManager.createDao(connectionSource, Choice.class);
        correctAnswerIntegerDao = DaoManager.createDao(connectionSource, CorrectAnswer.class);

        paperRecordIntegerDao = DaoManager.createDao(connectionSource, PaperRecord.class);
        answerIntegerDao = DaoManager.createDao(connectionSource, Answer.class);
        commentIntegerDao = DaoManager.createDao(connectionSource, Comment.class);

        userStringDao = DaoManager.createDao(connectionSource, User.class);



        // ???
        TableUtils.createTableIfNotExists(connectionSource, Paper.class);
        TableUtils.createTableIfNotExists(connectionSource, Category.class);
        TableUtils.createTableIfNotExists(connectionSource, Question.class);
        TableUtils.createTableIfNotExists(connectionSource, Choice.class);
        TableUtils.createTableIfNotExists(connectionSource, CorrectAnswer.class);

        TableUtils.createTableIfNotExists(connectionSource, PaperRecord.class);
        TableUtils.createTableIfNotExists(connectionSource, Answer.class);
        TableUtils.createTableIfNotExists(connectionSource, Comment.class);
        TableUtils.createTableIfNotExists(connectionSource, User.class);
        // ????????
        initializeAdmin();
    }

    private void initializeAdmin() throws Exception{
        try{
            User user = getUserByUsername("root");
            if (user==null){
                userStringDao.create(new User("root", "root".hashCode(), "???", User.Group.HeadTeacher));
                System.out.println("?????");
                userStringDao.create(new User("teacher", "treacher".hashCode(), "??", User.Group.Teachers));
                System.out.println("??????");
                userStringDao.create(new User("student", "student".hashCode(), "??", User.Group.Students));
                System.out.println("??????");
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    public User getUserByUsername(String username) throws SQLException{
        return userStringDao.queryForId(username);
    }

    public User loginByPassword(String username, String password) throws Exception{
        try{
            User user = userStringDao.queryForId(username);
            if (user.getHashPass()==password.hashCode()){
                return user;
            }
        } catch (Exception e){
            throw new Exception("?????");
        }
        return null;
    }

    public Dao<Paper, Integer> getPaperIntegerDao() {
        return paperIntegerDao;
    }

    public Dao<Category, Integer> getCategoryIntegerDao() {
        return categoryIntegerDao;
    }

    public Dao<Question, Integer> getQuestionIntegerDao() {
        return questionIntegerDao;
    }

    public Dao<Choice, Integer> getChoiceIntegerDao() {
        return choiceIntegerDao;
    }

    public Dao<CorrectAnswer, Integer> getCorrectAnswerIntegerDao() {
        return correctAnswerIntegerDao;
    }

    public Dao<PaperRecord, Integer> getPaperRecordIntegerDao() {
        return paperRecordIntegerDao;
    }

    public Dao<Answer, Integer> getAnswerIntegerDao() {
        return answerIntegerDao;
    }

    public Dao<Comment, Integer> getCommentIntegerDao() {
        return commentIntegerDao;
    }

    public Dao<User, String> getUserStringDao() {
        return userStringDao;
    }

    public static void main(String args[]){
        // ????
        try{
            DBHelper dbh = getDbHelper();
            System.out.println("?????");
            User root = dbh.getUserStringDao().queryForId("root");
            System.out.println(String.format("username:\t %s\npassword:\t %d\ngroup:\t %s\n",
                        root.getUsername(),
                        root.getHashPass(),
                        root.getGroup()
                    ));
            close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
