/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

import javax.ws.rs.core.Response;

/**
 * Created by portalBlock on 10/29/2014.
 *
 * @author http://blog.usul.org/cors-compliant-rest-api-with-jersey-and-containerresponsefilter/
 */
public class CrossDomainFilter implements ContainerResponseFilter {

    @Override
    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
        Response.ResponseBuilder resp = Response.fromResponse(response.getResponse());
        resp.header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");

        String reqHead = request.getHeaderValue("Access-Control-Request-Headers");

        if(reqHead != null && !reqHead.equals("")){
            resp.header("Access-Control-Allow-Headers", reqHead);
        }

        response.setResponse(resp.build());
        //Not Needed
        //response.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
        return response;
    }
}
