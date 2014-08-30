package net.portalblock.portalbot;

import jerklib.util.Colors;
import jline.console.ConsoleReader;
import net.portalblock.portalbot.features.*;
import net.portalblock.portalbot.features.management.*;
import jerklib.events.IRCEvent;
import jerklib.events.InviteEvent;
import jerklib.events.JoinCompleteEvent;
import jerklib.events.MessageEvent;
import jerklib.listeners.IRCEventListener;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by portalBlock on 1/9/14.
 */
public class EventListner implements IRCEventListener {
    private String[] login;
    public static String PREFIX;


    public EventListner(String[] loginIn){
        this.login = loginIn;
        PREFIX = "`";
    }


    public void receiveEvent(final IRCEvent e){
        if(e.getType() == IRCEvent.Type.CONNECT_COMPLETE){//Checks if event is the CONNECT_COMPLETE event to ghost(kill) all active users
            e.getSession().sayPrivate("nickserv", "GHOST "+login[1]+" "+login[2]);
            e.getSession().sayPrivate("nickserv", "IDENTIFY "+login[1]+" "+login[2]);
            e.getSession().join(login[3]);
            e.getSession().setRejoinOnKick(true);


        }else if(e.getType() == IRCEvent.Type.JOIN_COMPLETE){
            JoinCompleteEvent jce = (JoinCompleteEvent) e;
            e.getSession().mode(login[1], "+B");
           // e.getSession().sayChannel(jce.getChannel(), "Hi, I am "+login[1]+". An IRC bot made in Java!");

        }else if(e.getType() == IRCEvent.Type.INVITE_EVENT){
            InviteEvent ie = (InviteEvent) e;
            e.getSession().join(ie.getChannelName());



        }else if(e.getType() == IRCEvent.Type.CHANNEL_MESSAGE){
            final MessageEvent me = (MessageEvent) e;
            try{
                PortalBot.print(ConsoleReader.RESET_LINE + "[" + me.getChannel().getName() + "]" + me.getNick() + ": " + me.getMessage());
                PortalBot.getConsole().drawLine();
                PortalBot.getConsole().flush();
            }catch (IOException ex){
               // ex.printStackTrace();
            }
            String[] msgAr = me.getMessage().replaceFirst(PREFIX, "").split(" ");

            if(me.getMessage().startsWith(PREFIX)){
                if(msgAr.length >=1){
                    //FEATURES
                    ExecutorService service = Executors.newFixedThreadPool(2);
                    switch (msgAr[0].toLowerCase()){
                        //FUN STUFF
                        case "cookie": Cookie.cookie(me, e); return;
                        case "hug": Hug.hug(me, e); return;
                        case "cake": Cake.cake(me, e); return;
                        case "mcping":
                            service.execute(new Runnable() {
                                @Override
                                public void run() {
                                    MCPing.mcPing(me);
                                }
                            }); return;
                        case "mcp":
                            service.execute(new Runnable() {
                                @Override
                                public void run() {
                                    MCPing.mcPing(me);
                                }
                            }); return;
                        //MANAGMENT
                        case "op": OP.op(me, e); return;
                        case "deop": DeOP.deOP(me, e); return;
                        case "voice": Voice.voice(me, e); return;
                        case "devoice": DeVoice.deVoice(me, e); return;
                        case "kick": Kick.kick(me, e); return;
                        case "pastebin": me.getChannel().say("Has da pastebin: "+Colors.PURPLE+"http://pastebin.com/"); return;
                        case "g": Google.google(me, e); break;
                        case "remember": Remember.add(msgAr, me);
                        default: return;
                    }
                }
            }
            if(me.getMessage().startsWith("?")){
                Remember.triggerMemory(me, me.getMessage().replaceFirst("\\?", "").split(" ")[0]);
            }

            ExecutorService service = Executors.newFixedThreadPool(2);
            service.execute(new Runnable() {
                @Override
                public void run() {
                    new YouTubeListener(me, e);
                }
            });

        }
    }
}
