package edu.sjtu.SCEP.net.server;

import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-27
 * Time: 下午11:54
 * To change this template use File | Settings | File Templates.
 */

public class BackendServer extends ServerResource {

   public static void main(String[] args) throws Exception {
      // Create the HTTP server and listen on port 8182
      new Server(Protocol.HTTP, 8182, BackendServer.class).start();
   }

   @Get
   public String toString() {
      return "hello, world";
   }

}
