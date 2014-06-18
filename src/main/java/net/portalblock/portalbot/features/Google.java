package net.portalblock.portalbot.features;

import jerklib.events.IRCEvent;
import jerklib.events.MessageEvent;
import jerklib.util.Colors;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by portalBlock on 6/18/2014.
 */
public class Google {

    public static void google(MessageEvent me, IRCEvent e){
        String BASE = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=%query%";
        String[] msgAr = me.getMessage().replaceFirst("`", "").split(" ");
        if(msgAr.length >= 2){
            StringBuilder builder = new StringBuilder();
            for(int i = 1; i < msgAr.length; i++){
                builder.append(msgAr[i]+"%20");
            }
            String queryURL = BASE.replace("%query%", builder.toString().trim());
            try{
                URL link = new URL(queryURL);
                BufferedReader br = new BufferedReader(new InputStreamReader(link.openStream()));
                String line;
                StringBuilder jsonBuilder = new StringBuilder();
                while ((line = br.readLine()) != null){
                    jsonBuilder.append(line);
                }
                JSONObject totalResponse = new JSONObject(jsonBuilder.toString());
                JSONObject responseData = totalResponse.optJSONObject("responseData");
                if(responseData != null){
                    JSONArray results = responseData.optJSONArray("results");
                    if(results != null){
                        JSONObject result = results.optJSONObject(0);
                        if(result != null){
                            String title = result.getString("titleNoFormatting");
                            String url = result.getString("url");
                            String content = result.getString("content").replaceAll("<(/)?[bB]>", "").replaceAll("\\\n", " ");
                            String msg = Colors.PURPLE+Colors.BOLD+title+Colors.NORMAL+Colors.GREEN+" - "+Colors.PURPLE+url+Colors.GREEN+" - "+Colors.PURPLE+content;
                            me.getChannel().say("("+me.getNick()+") "+msg.replaceAll("[\\s\\s]+", " "));
                        }
                    }
                }
            }catch (IOException ex){

            }

        }else{
            e.getSession().notice(me.getNick(), "You must include the thing to search for!");
        }
    }

}
