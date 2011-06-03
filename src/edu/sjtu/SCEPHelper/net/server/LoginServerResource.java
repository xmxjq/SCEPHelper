package edu.sjtu.SCEPHelper.net.server;

import edu.sjtu.SCEPHelper.db.DBHelper;
import edu.sjtu.SCEPHelper.db.models.User;
import edu.sjtu.SCEPHelper.net.LoginResource;
import edu.sjtu.SCEPHelper.utils.StringUtils;
import org.restlet.data.CookieSetting;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;


/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-6-3
 * Time: 上午9:22
 * To change this template use File | Settings | File Templates.
 */
public class LoginServerResource extends ServerResource implements LoginResource{
    public User login(String username, String password){
        try{
            User user = DBHelper.getDbHelper().loginByPassword(username, password);
            CookieSetting cS = new CookieSetting(0, "username", StringUtils.encrypt(username));
            getResponse().getCookieSettings().add(cS);
            setStatus(Status.SUCCESS_OK);
            return user;
        }catch (Exception e){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e);
        }
    }

    public void logout() {
        getResponse().getCookieSettings().clear();
    }
}
