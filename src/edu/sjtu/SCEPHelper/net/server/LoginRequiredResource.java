package edu.sjtu.SCEPHelper.net.server;

import com.j256.ormlite.dao.Dao;
import edu.sjtu.SCEPHelper.db.DBHelper;
import edu.sjtu.SCEPHelper.db.models.User;
import edu.sjtu.SCEPHelper.utils.StringUtils;
import org.restlet.data.Cookie;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-6-3
 * Time: 上午10:05
 * To change this template use File | Settings | File Templates.
 */
public class LoginRequiredResource extends ServerResource{
    protected User requestUser = null;
    protected Dao<User, String> userStringDao = null;

    public LoginRequiredResource() throws Exception{
        super();
        userStringDao = (DBHelper.getDbHelper()).getUserStringDao();
    }

    @Override
    public void doInit() throws ResourceException {
        Series<Cookie> cookies = getRequest().getCookies();
        String requestUsername = cookies.getFirstValue("requestUser", null);

        try{
            if (requestUsername!=null) {
                requestUser = userStringDao.queryForId(StringUtils.decrypt(requestUsername));
            }
        }catch (SQLException e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
        }
    }

    protected boolean checkPermission(User.Group group){
        return (requestUser!=null && requestUser.getGroup()==group);
    }

    protected void hardCheckPermission(User.Group group) throws ResourceException{
        if (!checkPermission(group))
            throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN);
    }
}
