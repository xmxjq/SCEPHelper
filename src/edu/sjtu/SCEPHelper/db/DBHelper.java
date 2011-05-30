package edu.sjtu.SCEPHelper.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import edu.sjtu.SCEPHelper.db.models.*;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-23
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
public class DBHelper {
    private final static String DATABASE_URL = "jdbc:h2:mem:scep";

    // 设定各种Dao
    private Dao<Paper, Integer> paperIntegerDao;
	private Dao<Category, Integer> categoryIntegerDao;
    private Dao<Question, Integer> questionIntegerDao;
    private Dao<Choice, Integer> choiceIntegerDao;

    private Dao<PaperRecord, Integer> paperRecordIntegerDao;
    private Dao<Answer, Integer> answerIntegerDao;
    private Dao<Comment, Integer> commentIntegerDao;

    private Dao<User, String> userStringDao;

    private JdbcConnectionSource connectionSource = null;


    public DBHelper() throws Exception {
        connectionSource = new JdbcConnectionSource(DATABASE_URL);
		// 初始化数据库和Dao
		setupDatabase(connectionSource);
    }

    public void close(){
        try{
            if (connectionSource != null) {
		        connectionSource.close();
            }
        } catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    private void setupDatabase(ConnectionSource connectionSource) throws Exception {
        // 初始化各种Dao
        paperIntegerDao = DaoManager.createDao(connectionSource, Paper.class);
        categoryIntegerDao = DaoManager.createDao(connectionSource, Category.class);
        questionIntegerDao = DaoManager.createDao(connectionSource, Question.class);
        choiceIntegerDao = DaoManager.createDao(connectionSource, Choice.class);

        paperRecordIntegerDao = DaoManager.createDao(connectionSource, PaperRecord.class);
        answerIntegerDao = DaoManager.createDao(connectionSource, Answer.class);
        commentIntegerDao = DaoManager.createDao(connectionSource, Comment.class);

        userStringDao = DaoManager.createDao(connectionSource, User.class);



        // 创建表
        TableUtils.createTableIfNotExists(connectionSource, Paper.class);
        TableUtils.createTableIfNotExists(connectionSource, Category.class);
        TableUtils.createTableIfNotExists(connectionSource, Question.class);
        TableUtils.createTableIfNotExists(connectionSource, Choice.class);

        TableUtils.createTableIfNotExists(connectionSource, PaperRecord.class);
        TableUtils.createTableIfNotExists(connectionSource, Answer.class);
        TableUtils.createTableIfNotExists(connectionSource, Comment.class);
        TableUtils.createTableIfNotExists(connectionSource, User.class);
        // 初始化一个管理员
        initializeAdmin();
    }

    private void initializeAdmin() throws Exception{
        try{
            User user = getUserByUsername("root");
            if (user==null){
                userStringDao.create(new User("root", "root".hashCode(), "管理员", User.Group.HeadTeacher));
                System.out.println("创建管理员");
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
        }finally {
            throw new Exception("用户不存在");
        }
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
        // 测试数据
        try{
            DBHelper dbh = new DBHelper();
            System.out.println("查找管理员");
            User root = dbh.getUserStringDao().queryForId("root");
            System.out.println(String.format("username:\t %s\npassword:\t %d\ngroup:\t %s\n",
                        root.getUsername(),
                        root.getHashPass(),
                        root.getGroup()
                    ));
            dbh.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
