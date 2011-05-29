package edu.sjtu.SCEP.net;

import edu.sjtu.SCEP.db.models.User;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

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
    public void store(User user);

    @Delete
    public void remove();
}
