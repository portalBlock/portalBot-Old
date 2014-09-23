/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot;

import jerklib.Channel;
import net.portalblockz.portalbot.serverdata.ConnectionPack;
import net.portalblockz.portalbot.smarts.SmartListener;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by portalBlock on 9/1/2014.
 */
public class Utils {

    public static String getDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(calendar.getTime());
    }

    public static Response formResponse(String str){
        return formResponse(str, 200);
    }

    public static Response formResponse(String str, int code){
        return Response.status(code).entity(str).header("Server", "portalBot IRC Bot Webserver").build();
    }

    public static String getJSONServers(){
        JSONArray array = new JSONArray();
        for(ConnectionPack pack : PortalBot.getInstance().getConnections()){
            JSONObject server = new JSONObject();
            server.put("host", pack.getHost());
            //Make channels
            JSONArray channels = new JSONArray();
            for(Channel channel : pack.getSession().getChannels()){
                JSONObject chnl = new JSONObject();
                chnl.put("name", channel.getName());
                chnl.put("motd", channel.getTopic());
                chnl.put("people", channel.getNicks());
                channels.put(chnl);
            }
            server.put("channels", channels);
            //Add connection to array
            array.put(server);
        }
        return array.toString();
    }

}
