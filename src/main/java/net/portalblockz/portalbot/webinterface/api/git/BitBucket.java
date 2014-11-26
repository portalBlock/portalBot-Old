/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.webinterface.api.git;

import net.portalblockz.portalbot.Utils;
import net.portalblockz.portalbot.git.bitbucket.BitBucketPushEvent;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by portalBlock on 11/26/2014.
 */
@Path("bitbucket")
public class BitBucket {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(String body){
        if(body == null){
            return Utils.formResponse("You should not be here, SCRAM!", 400);
        }
        try{
            new BitBucketPushEvent().handle(URLDecoder.decode(body.replaceFirst("payload=", ""), "UTF-8"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return Utils.formResponse("Got it BitBucket, Thanks!");
    }

}
