package edu.sjtu.SCEPHelper.net.client;

import edu.sjtu.SCEPHelper.db.models.User;
import edu.sjtu.SCEPHelper.net.UserResource;
import org.restlet.resource.ClientResource;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-28
 * Time: 下午7:27
 * To change this template use File | Settings | File Templates.
 */
public class BackendClient {
    private ClientResource resource = null;
    private String url = null;
    private int port;

    public BackendClient(String url, int port) {
        this.url = url;
        this.port = port;

        resource = new ClientResource(String.format("%s:%d", url, port));
    }

    public User getUser(String username){
        resource.get();
        return null;
    }

    public static void main(String args[]) {
        ClientResource cr = new ClientResource("http://localhost:8182/user/root");
        UserResource resource = cr.wrap(UserResource.class);

        User user = resource.retrieve();
        if (user!=null){
            System.out.println("username:\t"+user.getUsername());
        }
    }
}
