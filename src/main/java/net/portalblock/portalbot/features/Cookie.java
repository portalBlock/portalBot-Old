package net.portalblock.portalbot.features;

import jerklib.events.IRCEvent;
import jerklib.events.MessageEvent;

/**
 * Created by portalBlock on 3/1/14.
 */
public class Cookie {

    public static void cookie(MessageEvent me, IRCEvent e){
        String[] msgAr = me.getMessage().replaceFirst("`", "").split(" ");
        if(msgAr.length >= 2){
            me.getChannel().action("gives "+msgAr[1]+" a cookie.");
        }else{
            e.getSession().notice(me.getNick(), "You must include the person you want to give the cookie to!");
        }
    }


}
