/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.webinterface.api.git;

import net.portalblockz.portalbot.Utils;
import net.portalblockz.portalbot.git.IGitEvent;
import net.portalblockz.portalbot.git.github.GitHubPullRequestEvent;
import net.portalblockz.portalbot.git.github.GitHubPushEvent;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by portalBlock on 9/21/2014.
 */
@Path("github")
public class GitHub {

    private static HashMap<String, Class<? extends IGitEvent>> eventHandlers;

    static {
        eventHandlers = new HashMap<>();
        eventHandlers.put("push", GitHubPushEvent.class);
        eventHandlers.put("pull_request", GitHubPullRequestEvent.class);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@HeaderParam("X-GitHub-Event") String type, String body){
        if(body == null || type == null){
            return Utils.formResponse("You should not be here, SCRAM!", 400);
        }
        Class<? extends IGitEvent> eventHandler = eventHandlers.get(type.toLowerCase());
        if(eventHandler != null){
            //eventHandler.handle(httpExchange);
            try{
                eventHandler.newInstance().handle(body);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(type.length() == 0){
            return Utils.formResponse("You should not be here, SCRAM!", 403);
        }else{
            return Utils.formResponse("Got it GitHub, Thanks!");
        }
    }

}
