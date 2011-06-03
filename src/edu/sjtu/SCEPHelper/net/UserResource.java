package edu.sjtu.SCEPHelper.net;

import edu.sjtu.SCEPHelper.db.models.User;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-29
 * Time: 下午3:18
 * To change this template use File | Settings | File Templates.
 */
public interface UserResource {
    @Get
    public User retrieve();

    @Put
    public void store(User user) throws ResourceException;

    @Delete
    public void remove();
}
