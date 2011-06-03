package edu.sjtu.SCEPHelper.net;

import edu.sjtu.SCEPHelper.db.models.User;
import org.restlet.data.Form;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-6-3
 * Time: 上午9:17
 * To change this template use File | Settings | File Templates.
 */
public interface LoginResource {
    @Post
    public User login(Form form) throws ResourceException;

    @Delete
    public void logout();
}
