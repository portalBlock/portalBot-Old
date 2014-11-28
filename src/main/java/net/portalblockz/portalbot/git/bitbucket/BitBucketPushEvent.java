/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.git.bitbucket;

import jerklib.util.Colors;
import net.portalblockz.portalbot.PortalBot;
import net.portalblockz.portalbot.git.IGitEvent;
import net.portalblockz.portalbot.serverdata.JSONConfigManager;
import net.portalblockz.portalbot.urlshorteners.URLShortener;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by portalBlock on 11/26/2014.
 */
public class BitBucketPushEvent extends IGitEvent {

    @Override
    public void handle(String body) {
        JSONObject object = new JSONObject(body);
        JSONArray array;
        String msg, name, repo;
        String disp = null;
        String shortUrl = null;
        name = Colors.RED+"An internal error has occurred and the author of this push was not readable.";
        msg = Colors.RED+"An internal error has occurred and the message of this push was not readable.";
        repo = Colors.RED+"An internal error has occurred and the repository name of this push was not readable.";
        if((array = object.optJSONArray("commits")) != null){
            JSONObject commit = array.getJSONObject(0);
            name = commit.getString("author");
            msg = commit.getString("message");
            repo = object.getJSONObject("repository").getString("name");
            shortUrl = URLShortener.getDefaultInstance().shorten(makeUrl(object.getString("canon_url"),
                    object.getJSONObject("repository").getString("absolute_url"), commit.getString("raw_node")));
            System.out.println("URL: " + shortUrl);
            disp = JSONConfigManager.getInstance().getRepoDispName(repo);
        }
        String totMsg = String.format(Colors.BLACK+"["+Colors.PURPLE+"%s"+Colors.BLACK+"] "+Colors.LIGHT_GRAY+"%s"+Colors.NORMAL+" has pushed: "+
                Colors.CYAN+"%s " + Colors.BLACK+"%s", disp, name, msg, shortUrl);
        //PortalBot.getInstance().globalSpeak(totMsg);
        PortalBot.getInstance().sayInChannels(totMsg, JSONConfigManager.getInstance().getChannelsForRepo(repo));
        PortalBot.getInstance().sayInChannels(shortUrl, JSONConfigManager.getInstance().getChannelsForRepo(repo));
    }

    private String makeUrl(String url, String absUrl, String node){
        return (url + absUrl + "commits/" + node);
    }
}
