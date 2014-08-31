package net.portalblock.portalbot.git;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.portalblock.portalbot.git.github.GitHubPushEvent;

import java.io.IOException;
import java.util.HashMap;
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

            }
        }
    }
}
