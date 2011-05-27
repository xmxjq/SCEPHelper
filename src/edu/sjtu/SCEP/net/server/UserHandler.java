package edu.sjtu.SCEP.net.server;

import com.j256.ormlite.dao.Dao;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.tools.internal.ws.wsdl.document.Output;
import edu.sjtu.SCEP.db.DBHelper;
import edu.sjtu.SCEP.db.models.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-27
 * Time: 下午5:06
 * To change this template use File | Settings | File Templates.
 */
public class UserHandler implements HttpHandler {

    private Dao<User, String> userStringDao = null;

    public UserHandler() throws Exception{
        //DBHelper dbh = new DBHelper();
        //userStringDao = dbh.getUserStringDao();
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        InputStream is = httpExchange.getRequestBody();
        String method = httpExchange.getRequestMethod();


        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(200, 0);

        OutputStream responseBody = httpExchange.getResponseBody();
        Headers requestHeaders = httpExchange.getRequestHeaders();
        Set<String> keySet = requestHeaders.keySet();
        Iterator<String> iter = keySet.iterator();

        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = is.read(b)) !=-1;){
            out.append(new String(b, 0, n));
        }
        responseBody.write(out.toString().getBytes());

        while (iter.hasNext()) {
            String key = iter.next();
            List values = requestHeaders.get(key);
            String s = key + " = " + values.toString() + "\n";
            responseBody.write(s.getBytes());

        }
        responseBody.close();

    }
}
