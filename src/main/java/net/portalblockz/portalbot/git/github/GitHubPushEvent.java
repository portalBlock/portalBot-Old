/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.git.github;

import com.sun.net.httpserver.HttpExchange;
import jerklib.util.Colors;
import net.portalblockz.portalbot.PortalBot;
import net.portalblockz.portalbot.git.IGitEvent;
import net.portalblockz.portalbot.serverdata.JSONConfigManager;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by portalBlock on 8/30/2014.
 */
public class GitHubPushEvent extends IGitEvent {

    @Override
    public void handle(String body){
        /*InputStream stream = httpExchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String s;
        StringBuilder jsonBuilder = new StringBuilder();
        try{
            while ((s = reader.readLine()) != null){
                jsonBuilder.append(s);
            }
        }catch (IOException e){

        }*/
        JSONObject object = new JSONObject(body);
        JSONObject array;
        String msg, name, repo;
        String disp = null;
        name = Colors.RED+"An internal error has occurred and the author of this push was not readable.";
        msg = Colors.RED+"An internal error has occurred and the message of this push was not readable.";
        repo = Colors.RED+"An internal error has occurred and the repository name of this push was not readable.";
        if((array = object.optJSONObject("head_commit")) != null){
            msg = String.valueOf(object.getJSONArray("commits").getJSONObject(0).getString("message"));
            JSONObject commiter = array.optJSONObject("committer");
            if(commiter != null){
                name = commiter.getString("name");
            }
        }
        JSONObject repoJ = object.optJSONObject("repository");
        if(repoJ != null){
            repo = repoJ.getString("name");
            disp = JSONConfigManager.getInstance().getRepoDispName(repo);
        }
        String totMsg = String.format(Colors.BLACK+"["+Colors.PURPLE+"%s"+Colors.BLACK+"] "+Colors.LIGHT_GRAY+"%s"+Colors.NORMAL+" has pushed: "+Colors.CYAN+"%s", (disp == null ? repo : disp), name, msg);
        //PortalBot.getInstance().globalSpeak(totMsg);
        if(msg.startsWith("**")){
            return;
        }
        PortalBot.getInstance().sayInChannels(totMsg, JSONConfigManager.getInstance().getChannelsForRepo(repo));
    }

}
