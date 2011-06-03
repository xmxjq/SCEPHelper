package edu.sjtu.SCEPHelper.net;

import edu.sjtu.SCEPHelper.db.models.PaperRecord;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-6-3
 * Time: 上午9:51
 * To change this template use File | Settings | File Templates.
 */
public interface RecordResource {
    @Get
    public PaperRecord retrieve();

    @Post
    public void submit(PaperRecord paperRecord);

    @Put
    public void correction(PaperRecord paperRecord);

    @Delete
    public void remove();
}
