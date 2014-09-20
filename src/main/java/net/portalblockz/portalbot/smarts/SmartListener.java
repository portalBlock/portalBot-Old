/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.smarts;

import jerklib.events.IRCEvent;
import jerklib.events.MessageEvent;
import jerklib.listeners.IRCEventListener;
import jerklib.util.Colors;
import net.portalblockz.portalbot.serverdata.Server;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by portalBlock on 9/14/2014.
 */
public class SmartListener implements IRCEventListener {
    private final int MAX_WARNS = 3;
    private static Map<String, IRCUser> users = new HashMap<>();
    private Map<String, String> lastSaid = new HashMap<>();
    private Map<String, Long> chatCooldown = new HashMap<>();
    private Server server;

    public SmartListener(Server s){
        this.server = s;
    }

    @Override
    public void receiveEvent(IRCEvent ircEvent) {
        if(ircEvent.getType() == IRCEvent.Type.CHANNEL_MESSAGE){
            MessageEvent me = (MessageEvent)ircEvent;
            if(users.get(me.getNick().toLowerCase()) == null) users.put(me.getNick().toLowerCase(), new IRCUser(me.getNick()));
            checkLastSaid(me);
            checkCoolCheck(me);
            checkCaps(me);
        }
    }

    private void checkCoolCheck(MessageEvent e){
        if(server.getStaff().contains(e.getNick())) return;
        if(coolCheck(e.getNick())){
            chatCooldown.put(e.getNick(), System.currentTimeMillis());
        }else{
            IRCUser user = users.get(e.getNick().toLowerCase());
            user.setSpam(user.getSpam()+1);
            if(user.getSpam() >= MAX_WARNS){
                e.getChannel().kick(e.getNick(), "Please do not spam the chat!");
                //chatCooldown.remove(e.getNick());
            }else{
                e.getSession().notice(e.getNick(), Colors.RED+"Please do not spam things! Warning "+user.getSpam()+"/3");
            }
        }
    }

    private void checkLastSaid(MessageEvent e){
        if(server.getStaff().contains(e.getNick())) return;
        String lastMessage, message;
        message = e.getMessage();
        lastMessage = lastSaid.get(e.getNick());
        if(lastMessage != null) {
            int diff = message.length() - lastMessage.length();
            if (message.equalsIgnoreCase(lastMessage) || message.contains(lastMessage) && diff <= 3) {
                IRCUser user = users.get(e.getNick().toLowerCase());
                user.setRepeat(user.getRepeat()+1);
                if(user.getRepeat() >= MAX_WARNS){
                    e.getChannel().kick(e.getNick(), "Please do not repeat things more then 3 times!");
                }else{
                    e.getSession().notice(e.getNick(), Colors.RED+"Please do not repeat things! Warning "+user.getRepeat()+"/3");
                    lastSaid.remove(e.getNick());
                }
            }
        }
        lastSaid.put(e.getNick(), e.getMessage());
    }

    public void checkCaps(MessageEvent e){
        if(server.getStaff().contains(e.getNick())) return;
        double upperCaseCount = 0;
        String message = e.getMessage();
        for (int i = 0; i < message.length(); i++){
            if(Character.isUpperCase(message.charAt(i))){
                upperCaseCount++;
            }
        }
        double maxPer = 50;
        double tempNum = upperCaseCount / message.length();
        double percent = tempNum * 100;
        if (percent >= maxPer) {
            IRCUser user = users.get(e.getNick().toLowerCase());
            user.setCaps(user.getCaps()+1);
            if(user.getCaps() >= MAX_WARNS){
                e.getChannel().kick(e.getNick(), "Please do not use so much caps!");
            }else{
                e.getSession().notice(e.getNick(), Colors.RED+"Please don't use more then 50% caps in a message!");
            }
        }
    }

    public boolean coolCheck(String name) {
        final int delay = 2;
        if (chatCooldown.get(name) != null) {
            return chatCooldown.get(name) < (System.currentTimeMillis() - delay * 1000);
        } else {
            return true;
        }
    }

    public static String getJSONInfo(){
        JSONArray content = new JSONArray();
        for(Map.Entry<String, IRCUser> entry : users.entrySet()){
            JSONObject user = new JSONObject();
            user.put("name", entry.getValue().getName());
            user.put("caps", entry.getValue().getCaps());
            user.put("spam", entry.getValue().getSpam());
            user.put("repeat", entry.getValue().getRepeat());
            content.put(user);
        }
        return content.toString();
    }

    public static void flushUser(String name){
        users.remove(name.toLowerCase());
    }

}
