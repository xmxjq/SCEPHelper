package edu.sjtu.SCEPHelper.net.server;

import edu.sjtu.SCEPHelper.db.DBHelper;
import edu.sjtu.SCEPHelper.db.models.Answer;
import edu.sjtu.SCEPHelper.db.models.PaperRecord;
import edu.sjtu.SCEPHelper.db.models.User;
import edu.sjtu.SCEPHelper.net.RecordResource;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;

import java.awt.print.Paper;
import java.util.ArrayList;

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
        if (recordStringId!=null && !recordStringId.equals("")){
            try{
                paperRecord = DBHelper.getDbHelper().getPaperRecordIntegerDao().queryForId(Integer.parseInt(recordStringId));
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    public PaperRecord retrieve() {
        if(checkPermission(User.Group.Students) &&
                !requestUser.getUsername().equals(paperRecord.getUser().getUsername())) {
            throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN);
        }
        if (paperRecord!=null){
            paperRecord.setSerializableAnswers(retrieveAnswers(paperRecord));
        }
        return paperRecord;
    }

    private ArrayList<Answer> retrieveAnswers(PaperRecord paperRecord) {
        try{
            return Answer.queryByPaperRecord(paperRecord);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void submit(PaperRecord paperRecord) throws ResourceException {
        hardCheckPermission(User.Group.Students);
        try{
            System.out.println("SUBMIT: "+paperRecord.getPaper().getName());
            DBHelper.getDbHelper().getPaperRecordIntegerDao().create(paperRecord);
            submitAnswers(paperRecord.getSerializableAnswers(), paperRecord);
        }catch (Exception e){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e);
        }
    }

    private void submitAnswers(ArrayList<Answer> answers, PaperRecord paperRecord) {
        try{
            for(Answer answer: answers){
                answer.setPaperRecord(paperRecord);
                DBHelper.getDbHelper().getAnswerIntegerDao().create(answer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void correction(PaperRecord paperRecord) throws ResourceException {
        hardCheckPermission(User.Group.Teachers);
        try{
            correctAnswers(paperRecord.getSerializableAnswers(), paperRecord);
        }catch (Exception e){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e);
        }
    }

    private void correctAnswers(ArrayList<Answer> answers, PaperRecord paperRecord) {
        try{
            for(Answer answer: answers){
                if(answer.getComment().getId()==0){
                    DBHelper.getDbHelper().getCommentIntegerDao().create(answer.getComment());
                }else{
                    DBHelper.getDbHelper().getCommentIntegerDao().update(answer.getComment());
                }
                answer.setPaperRecord(paperRecord);
                DBHelper.getDbHelper().getAnswerIntegerDao().update(answer);
            }
        }catch (Exception e){
            e.printStackTrace();
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
