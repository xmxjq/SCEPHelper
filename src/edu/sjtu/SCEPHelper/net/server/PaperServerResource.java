package edu.sjtu.SCEPHelper.net.server;

import edu.sjtu.SCEPHelper.db.DBHelper;
import edu.sjtu.SCEPHelper.db.models.Paper;
import edu.sjtu.SCEPHelper.db.models.User;
import edu.sjtu.SCEPHelper.net.PaperResource;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import javax.jws.soap.SOAPBinding;
import javax.swing.text.PlainDocument;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-6-3
 * Time: 上午10:22
 * To change this template use File | Settings | File Templates.
 */
public class PaperServerResource extends LoginRequiredResource implements PaperResource{
    private Paper paper = null;

    public PaperServerResource() throws Exception {
        super();
    }

    @Override
    public void doInit() throws ResourceException {
        super.doInit();
        String paperStringId = getRequest().getResourceRef().getQueryAsForm().getFirstValue("paper_id");
        if (paperStringId!=null && paperStringId.equals("")){
            try{
                paper = DBHelper.getDbHelper().getPaperIntegerDao().queryForId(Integer.parseInt(paperStringId));
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    public Paper retrieve() {
        return paper;
    }

    public void create(Paper paper){
        hardCheckPermission(User.Group.HeadTeacher);
        try{
            DBHelper.getDbHelper().getPaperIntegerDao().create(paper);
        }catch (Exception e){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e);
        }
    }

    public void update(Paper paper){
        hardCheckPermission(User.Group.HeadTeacher);
        try{
            DBHelper.getDbHelper().getPaperIntegerDao().update(paper);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void remove() {
        hardCheckPermission(User.Group.HeadTeacher);
        try{
            if (paper!=null)
                DBHelper.getDbHelper().getPaperIntegerDao().delete(paper);
        }catch (Exception e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e);
        }
    }
}
