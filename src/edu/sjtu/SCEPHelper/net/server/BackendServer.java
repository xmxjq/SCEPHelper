package edu.sjtu.SCEPHelper.net.server;

import edu.sjtu.SCEPHelper.net.ResourceURL;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-27
 * Time: 下午11:54
 * To change this template use File | Settings | File Templates.
 */

public class BackendServer extends Application {
    private static Component component = null;


    public static Component getComponent(){
        if (component==null){
            component = new Component();
            component.getServers().add(Protocol.HTTP, 8182);
            component.getDefaultHost().attach(new BackendServer());

            return component;
        } else {
            return component;
        }
    }

    public static void startServer() throws Exception {
       getComponent().start();
    }

    public static void stopServer() throws Exception {
        getComponent().stop();
    }


    public static void main(String[] args) throws Exception {
        try {
            BackendServer.startServer();
        } catch (Exception e) {
            // Something is wrong.
            e.printStackTrace();
        }

    }

    @Override
    public synchronized Restlet createInboundRoot(){
        Router router = new Router(getContext());

        //router.attachDefault(new Directory(getContext(), "war:///"));
        router.attach(ResourceURL.ROOT_URL, RootServerResource.class);
        router.attach(ResourceURL.USER_URL, UserServerResource.class);

        return router;
    }



}
