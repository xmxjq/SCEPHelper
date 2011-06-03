package edu.sjtu.SCEPHelper.net.server;

import edu.sjtu.SCEPHelper.db.DBHelper;
import edu.sjtu.SCEPHelper.db.models.PaperRecord;
import edu.sjtu.SCEPHelper.db.models.User;
import edu.sjtu.SCEPHelper.net.RecordResource;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-6-3
 * Time: 上午11:09
 * To change this template use File | Settings | File Templates.
 */
public class RecordServerResource extends LoginRequiredResource implements RecordResource {
    private PaperRecord paperRecord = null;

    public RecordServerResource() throws Exception {
        super();
    }

    @Override
    public void doInit() throws ResourceException {
        super.doInit();
        String recordStringId = getRequest().getResourceRef().getQueryAsForm().getFirstValue("record_id");
        if (recordStringId!=null && recordStringId.equals("")){
            try{
                paperRecord = DBHelper.getDbHelper().getPaperRecordIntegerDao().queryForId(Integer.parseInt(recordStringId));
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    public PaperRecord retrieve() {
        return paperRecord;
    }

    public void submit(PaperRecord paperRecord) throws ResourceException {
        hardCheckPermission(User.Group.Students);
        try{
            DBHelper.getDbHelper().getPaperRecordIntegerDao().create(paperRecord);
        }catch (Exception e){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e);
        }
    }

    public void correction(PaperRecord paperRecord) throws ResourceException {
        hardCheckPermission(User.Group.Teachers);
        try{
            DBHelper.getDbHelper().getPaperRecordIntegerDao().create(paperRecord);
        }catch (Exception e){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e);
        }
    }

    public void remove() throws ResourceException{
        if (!((checkPermission(User.Group.Students)
                &&(paperRecord!=null)
                &&(requestUser.getUsername().equals(paperRecord.getUser().getUsername()))))){
                throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN);
        }
        try{
            DBHelper.getDbHelper().getPaperRecordIntegerDao().delete(paperRecord);
        }catch (Exception e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e);
        }

    }
}
