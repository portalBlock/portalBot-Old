package net.portalblockz.portalbot.webinterface;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.portalblockz.portalbot.Utils;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by portalBlock on 9/1/2014.
 */
public class WebIntHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //TODO: Make this dynamic with a page, add support for multiple pages, make it actually display info!
        String header = "<h1 style=\"color:red\">portalBot IRC Bot</h1>";
        String text =
                "<p>Welcome this is the beginning of a web interface for portalBot, its a WIP and right now does not have anything but this message on it!</p>";
        String footer = "<center>&copy; portalBlock 2014</center>";
        sendReply(httpExchange, header+text+footer);
        String uri = "html/"+httpExchange.getRequestURI();
    }

    private void sendReply(HttpExchange httpExchange, String msg){
        sendReply(httpExchange, msg, "text/html");
    }

    private void sendReply(HttpExchange httpExchange, String msg, String ctntType){
        try{
            String response = "<html><head><title>portalBot</title></head><body>"+msg+"</body></html>";
            Map<String, String> newHeaders = new HashMap<>();
            newHeaders.put("Date", Utils.getDate());
            newHeaders.put("Server", "portalBot IRC Bot Webserver");
            newHeaders.put("Content-Length", response.length()+"");
            newHeaders.put("Content-Type", ctntType);
            for(Map.Entry<String, String> entry : newHeaders.entrySet()){
                List<String> l = new ArrayList<>();
                l.add(entry.getValue());
                httpExchange.getResponseHeaders().put(entry.getKey(), l);
            }
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.flush();
            os.close();
            httpExchange.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String readFile(File file){
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(" ");
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String s;
            while ((s = reader.readLine()) != null){
                responseBuilder.append(s);
            }
        }catch (Exception e){

        }
        return responseBuilder.toString();
    }
}
