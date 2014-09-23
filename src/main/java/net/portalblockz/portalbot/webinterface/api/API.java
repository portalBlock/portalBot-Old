/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.webinterface.api;

import net.portalblockz.portalbot.Utils;
import net.portalblockz.portalbot.serverdata.JSONConfigManager;
import net.portalblockz.portalbot.smarts.SmartListener;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by portalBlock on 9/21/2014.
 */
@Path("api")
public class API {

    @GET
    @Path("warns")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWarns() {
        return Utils.formResponse(SmartListener.getJSONInfo());
    }

    @GET
    @Path("networks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNetworks(){
        return Utils.formResponse(Utils.getJSONServers());
    }
}
