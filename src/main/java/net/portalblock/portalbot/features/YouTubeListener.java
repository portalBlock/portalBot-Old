package net.portalblock.portalbot.features;

import jerklib.events.IRCEvent;
import jerklib.events.MessageEvent;
import jerklib.util.Colors;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by portalBlock on 3/1/14.
 */
public class YouTubeListener {

    public YouTubeListener(MessageEvent me, IRCEvent e){
        String id = null;

        for(String temp : me.getMessage().split(" ")){
            if(getID(temp) != null){
                id = getID(temp);
            }
        }

        String json = "";
        JSONObject object = null;
        if(id != null){
            try{
                URL link = new URL("https://gdata.youtube.com/feeds/api/videos/"+id+"?v=2&alt=json");
                BufferedReader br = new BufferedReader(new InputStreamReader(link.openStream()));

                String input;
                while((input = br.readLine()) != null){
                    json+=input;
                    object = new JSONObject(json);
                }
                br.close();
                if(object == null){
                    return;
                }
                String title = object.getJSONObject("entry").getJSONObject("title").getString("$t");
                JSONArray array = object.getJSONObject("entry").getJSONArray("author");
                String author = array.getJSONObject(0).getJSONObject("name").getString("$t");
                int duration = object.getJSONObject("entry").getJSONObject("media$group").getJSONObject("yt$duration").getInt("seconds");
                int min = duration/60;
                double sec = duration%60;
                int views = object.getJSONObject("entry").getJSONObject("yt$statistics").getInt("viewCount");
                int dislikes = object.getJSONObject("entry").getJSONObject("yt$rating").getInt("numDislikes");
                int likes = object.getJSONObject("entry").getJSONObject("yt$rating").getInt("numLikes");
                int total = dislikes + likes;
                double percent = likes/total*100;
                me.getChannel().say("("+me.getNick()+") "+Colors.DARK_GREEN+title+Colors.BLACK+" by "+Colors.DARK_GREEN+author+Colors.BLACK+" - views: "+Colors.DARK_GREEN+views+Colors.BLACK+" - likes: "+Colors.DARK_GREEN+percent+"%"+Colors.BLACK+" - length: "+Colors.DARK_GREEN+min+"m "+sec+"s");

            }catch (MalformedURLException e1){
                e1.printStackTrace();
            }catch (IOException e1){
                e1.printStackTrace();
            }



        }
    }

    private String getID(String str){
        for(String s : str.split(" ")){
            String regex = "(https?:\\/\\/)?(www.)?youtu\\.be\\/";
            Pattern pat = Pattern.compile(regex);
            Matcher m = pat.matcher(s);
            if(m.find()){
                return str.replaceFirst(m.group(), "");
            }

        }


        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(str);

        if(matcher.find()){
            return matcher.group();
        }

        return null;
    }
}
