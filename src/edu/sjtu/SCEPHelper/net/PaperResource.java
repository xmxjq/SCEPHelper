package edu.sjtu.SCEPHelper.net;

import edu.sjtu.SCEPHelper.db.models.Paper;
import org.restlet.resource.*;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-6-3
 * Time: 上午9:55
 * To change this template use File | Settings | File Templates.
 */
public interface PaperResource {
    @Get
    public Paper retrieve();

    @Post
    public void create(Paper paper) throws ResourceException;

    @Put
    public void update(Paper paper) throws ResourceException;

    @Delete
    public void remove() throws ResourceException;

}
