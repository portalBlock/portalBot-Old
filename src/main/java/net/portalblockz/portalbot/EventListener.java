package net.portalblockz.portalbot;

import jerklib.events.IRCEvent;
import jerklib.events.InviteEvent;
import jerklib.events.MessageEvent;
import jerklib.events.NoticeEvent;
import jerklib.listeners.IRCEventListener;
import net.portalblockz.portalbot.serverdata.ConnectionPack;
import net.portalblockz.portalbot.serverdata.Server;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class EventListener implements IRCEventListener {
    private ConnectionPack pack;
    private Server server;

    public EventListener(ConnectionPack pack, Server server){
        this.pack = pack;
        this.server = server;
    }

    @Override
    public void receiveEvent(IRCEvent ircEvent) {
        //System.out.println(ircEvent.toString());
        switch (ircEvent.getType()){
            case CONNECT_COMPLETE:
                pack.getSession().setRejoinOnKick(true);
                pack.getSession().sayPrivate("nickserv", "GHOST " + server.getUsername() + " " + server.getPass());
                pack.getSession().sayPrivate("nickserv", "IDENTIFY " + server.getUsername() + " " + server.getPass());
                pack.getSession().mode(server.getUsername(), "+B");
                for(String s : server.getChannels()){
                    pack.getSession().join(s);
                }
                break;
            case CHANNEL_MESSAGE:
                MessageEvent me = (MessageEvent) ircEvent;
                handle(me);
                System.out.println(me.getNick()+": "+me.getMessage());
                break;
            case INVITE_EVENT:
                InviteEvent ie = (InviteEvent) ircEvent;
                pack.getSession().join(ie.getChannelName());
                break;
            case PRIVATE_MESSAGE:
                MessageEvent pm = (MessageEvent) ircEvent;
                handle(pm);
                System.out.println(pm.getNick()+": "+pm.getMessage());
                break;
            case NOTICE:
                NoticeEvent ne = (NoticeEvent) ircEvent;
                System.out.println(ne.getNoticeMessage());
                break;
            default: break;
        }
    }

    private void handle(MessageEvent e){

    }
}
