package net.portalblock.portalbot.features.consolecommands;

import jerklib.Channel;
import net.portalblock.portalbot.PortalBot;

/**
 * Created by portalBlock on 3/4/14.
 */
public class Kick {

    public static void kick(String name){
        for(Channel channel : PortalBot.session.getChannels()){
            channel.kick(name, "Kicked from the server.");
        }
    }
}
