package edu.sjtu.SCEPHelper.net.server;

import com.j256.ormlite.dao.Dao;
import edu.sjtu.SCEPHelper.db.DBHelper;
import edu.sjtu.SCEPHelper.db.models.User;
import edu.sjtu.SCEPHelper.net.UserResource;
import org.restlet.data.Cookie;
import org.restlet.data.Status;
import org.restlet.resource.*;
import org.restlet.util.Series;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-29
 * Time: 下午3:22
 * To change this template use File | Settings | File Templates.
 */
public class UserServerResource extends ServerResource implements UserResource{

    private User user = null;
    private User requestUser = null;
    private Dao<User, String> userStringDao = null;

    public UserServerResource() throws Exception{
        super();
        userStringDao = (new DBHelper()).getUserStringDao();

    }

    @Override
    public void doInit() throws ResourceException{
        Series<Cookie> cookies = getRequest().getCookies();
        String username = (String) getRequest().getAttributes().get("username");
        String requestUsername = cookies.getFirstValue("requestUser", null);

        try{
            user = userStringDao.queryForId(username);
            if (requestUsername!=null) {
                requestUser = userStringDao.queryForId(requestUsername);
            }
        }catch (SQLException e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
        }
    }


    @Get
    public User retrieve() {
        try{
            user = userStringDao.queryForId("root");
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return user;
    }

    @Put
    public void store(User user) {
        //只有本人或者校长才能修改用户
        if (requestUser != null &&
                (requestUser.getGroup() == User.Group.HeadTeacher ||
                        requestUser.getUsername().equals(user.getUsername()) )){
            try{
                userStringDao.update(user);
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    @Delete
    public void remove() {
        try{
            userStringDao.delete(user);
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
