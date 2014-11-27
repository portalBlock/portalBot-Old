/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.serverdata;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class JSONConfigManager {

    private JSONObject configObject;
    private List<Server> serverList = new ArrayList<>();
    private Map<String, List<String>> repoMap = new HashMap<>();
    private Map<String, String> repoDispNames = new HashMap<>();
    private List<String> blacklistWords = new ArrayList<>();
    private static JSONConfigManager instance;

    public static JSONConfigManager getInstance() {
        return instance;
    }

    public JSONConfigManager(File file){
        instance = this;
        try{
            if(!file.exists()){
                Path path = file.toPath();
                Files.copy(getClass().getClassLoader().getResourceAsStream("config.json"), path);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        String s;
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            StringBuilder jsonObjectBuilder = new StringBuilder();
            while ((s = reader.readLine()) != null){
                jsonObjectBuilder.append(s);
            }
            configObject = new JSONObject(jsonObjectBuilder.toString());
            reader.close();

        }catch (Exception e){

        }
        for(int i = 0; i < getJSONServers().length(); i++){
            JSONObject server = getJSONServers().getJSONObject(i);
            serverList.add(new Server(server.getString("host"), server.getString("username"), server.getString("password"), server.getInt("port"), getList(server, "channels"), getList(server, "staff"), server.getString("prefix").charAt(0)));
            System.out.println(String.format("%s %s %s %s %s", server.getString("host"), server.getString("username"), server.getString("password"), server.getInt("port"), server.getString("prefix").charAt(0)));
        }
        serializeRepos();
        serializeBlacklist();
    }

    public void serializeBlacklist(){
        JSONArray blackList = configObject.optJSONArray("blacklist-words");
        if(blackList != null){
            for(int i = 0; i < blackList.length(); i++){
                if(!blacklistWords.contains(blackList.getString(i).toLowerCase())){
                    blacklistWords.add(blackList.getString(i).toLowerCase());
                }
            }
        }
    }

    public boolean isBlacklistedWord(String[] s){
        for(String str : s){
            if(blacklistWords.contains(str.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public void addBlacklistWord(String s){
        if(!blacklistWords.contains(s.toLowerCase())){
            blacklistWords.add(s.toLowerCase());
        }
    }

    public void serializeRepos(){
        JSONArray repoArray = configObject.optJSONArray("git-repos");
        if(repoArray != null){
            for(int i = 0; i < repoArray.length(); i++){
                JSONObject repoData = repoArray.optJSONObject(i);
                if(repoData != null){
                    String name = repoData.getString("name");
                    String dispName = repoData.optString("dispName");
                    dispName = (dispName == null) ? name : dispName;
                    List<String> repoChannels = new ArrayList<>();
                    for(int n = 0; n < repoData.getJSONArray("channels").length(); n++){
                        repoChannels.add(repoData.getJSONArray("channels").getString(n).toLowerCase());
                    }
                    repoMap.put(name.toLowerCase(), repoChannels);
                    repoDispNames.put(name.toLowerCase(), dispName);
                }
            }
        }
    }

    public String getRepoDispName(String name){
        return repoDispNames.get(name.toLowerCase());
    }

    public List<String> getChannelsForRepo(String repoName){
        return repoMap.get(repoName.toLowerCase());
    }

    public Server[] getServers(){
        return serverList.toArray(new Server[serverList.size()]);
    }

    public JSONArray getJSONServers(){
        return configObject.getJSONArray("servers");
    }

    public List<String> getList(JSONObject server, String key){
        List<String> value = new ArrayList<>();
        for(int i = 0; i < server.getJSONArray(key).length(); i++){
            value.add(server.getJSONArray(key).getString(i));
        }
        return value;
    }

}
