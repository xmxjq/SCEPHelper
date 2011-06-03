package edu.sjtu.SCEPHelper.net.server;

import com.j256.ormlite.dao.Dao;
import edu.sjtu.SCEPHelper.db.DBHelper;
import edu.sjtu.SCEPHelper.db.models.User;
import edu.sjtu.SCEPHelper.net.UserResource;
import edu.sjtu.SCEPHelper.utils.StringUtils;
import org.restlet.data.Cookie;
import org.restlet.data.Status;
import org.restlet.resource.*;
import org.restlet.util.Series;

import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-29
 * Time: 下午3:22
 * To change this template use File | Settings | File Templates.
 */
public class UserServerResource extends LoginRequiredResource implements UserResource{

    private User user = null;
    private User requestUser = null;

    public UserServerResource() throws Exception {
        super();
    }

    @Override
    public void doInit() throws ResourceException{
        String username = (String) getRequest().getAttributes().get("username");
        try{
            user = userStringDao.queryForId(username);
            super.doInit();
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
    public void store(User user){
        //只有本人或者校长才能修改用户
        if (requestUser != null &&
                (checkPermission(User.Group.HeadTeacher.HeadTeacher) ||
                        requestUser.getUsername().equals(user.getUsername()) )){
            try{
                userStringDao.update(user);
            } catch (Exception e){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e);
            }
        }else
            throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN);
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
