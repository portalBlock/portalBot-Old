package net.portalblock.portalbot.features;

import jerklib.events.MessageEvent;
import jerklib.util.Colors;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by portalBlock on 3/5/14.
 */
public class MCPing {

    public static void mcPing(MessageEvent me){
        String[] args  = me.getMessage().split(" ");

        if(args.length < 2){
            me.getSession().notice(me.getNick(), Colors.RED+"Please include the address to ping!");
            return;
        }

        String[] set = args[1].split(":");

        String address = set[0];
        int port = 25565;
        if(set.length >= 2){
            port = Integer.parseInt(set[1]);
        }

        PingerUtil util = new PingerUtil();
        util.setAddress(new InetSocketAddress(address, port));
        try{
            PingerUtil.StatusResponse response  = util.fetchData();
            String motd = response.getDescription();
            String version = response.getVersion().getName();
            String cur = response.getPlayers().getOnline()+"";
            String max = response.getPlayers().getMax()+"";
            String text = "("+me.getNick()+") "+ChatTrans.translateColorCodes(motd)+Colors.RED+" - "+version+" - "+cur+"/"+max;
            me.getChannel().say(text.replaceAll("null", ""));
        }catch (IOException e){
            e.printStackTrace();
            me.getChannel().say(Colors.RED+"Encountered an error!");
        }

    }
}
