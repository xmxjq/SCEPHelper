package edu.sjtu.ETHelper.net.server;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-29
 * Time: 下午3:59
 * To change this template use File | Settings | File Templates.
 */
public class RootServerResource extends ServerResource{
    @Get
    public String represent(){
        return "Server works";
    }
}
