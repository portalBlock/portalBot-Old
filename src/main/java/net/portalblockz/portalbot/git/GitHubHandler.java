package net.portalblockz.portalbot.git;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.portalblockz.portalbot.Utils;
import net.portalblockz.portalbot.git.github.GitHubPushEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by portalBlock on 8/30/2014.
 */
public class GitHubHandler implements HttpHandler {

    private Map<String, Class<? extends IGitEvent>> eventHandlers = new HashMap<>();

    public GitHubHandler(){
        eventHandlers.put("push", GitHubPushEvent.class);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String type = httpExchange.getRequestHeaders().getFirst("X-Github-Event");
        Class<? extends IGitEvent> eventHandler = eventHandlers.get(type.toLowerCase());
        if(eventHandler != null){
            //eventHandler.handle(httpExchange);
            try{
                eventHandler.newInstance().handle(httpExchange);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(type == null){
            sendReply(httpExchange, "You should not be here, SCRAM!");
            return;
        }else{
            sendReply(httpExchange, "Got it GitHub, Thanks!");
        }
        System.out.println("GitHub event received!");
    }

    private void sendReply(HttpExchange httpExchange, String msg){
        try{
            String response = "<html><head><title>portalBot</title></head><body><h1>"+msg+"</h1></body></html>";
            Map<String, String> newHeaders = new HashMap<>();
            newHeaders.put("Date", Utils.getDate());
            newHeaders.put("Server", "portalBot IRC Bot Webserver");
            newHeaders.put("Content-Length", response.length()+"");
            newHeaders.put("Content-Type", "text/html");
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
}
