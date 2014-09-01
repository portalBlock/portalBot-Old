package net.portalblockz.portalbot.command;

import jerklib.Session;
import net.portalblockz.portalbot.serverdata.Server;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class UserCommandSender implements CommandSender{
    private String name;
    private String channel;
    private Session session;
    private Server server;


    public UserCommandSender(String name, String channel, Session session, Server server) {
        this.name = name;
        this.channel = channel;
        this.session = session;
        this.server = server;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void sendMessage(String s) {
        session.notice(name, s);
    }

    public Server getServer(){
        return server;
    }

    public boolean isStaff(Server s){
        return s.getStaff().contains(name);
    }

    public String getChannel() {
        return channel;
    }

    public Session getSession() {
        return session;
    }
}
