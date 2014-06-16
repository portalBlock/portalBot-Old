package net.portalblock.portalbot.features.consolecommands;

import net.portalblock.portalbot.PortalBot;

/**
 * Created by portalBlock on 3/4/14.
 */
public class Join {

    public static void join(String channel){
        PortalBot.session.join(channel);
    }
}
