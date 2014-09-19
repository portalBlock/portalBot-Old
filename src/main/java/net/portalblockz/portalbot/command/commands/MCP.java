/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.command.commands;

import jerklib.util.Colors;
import net.portalblockz.portalbot.ChatColor;
import net.portalblockz.portalbot.ChatTrans;
import net.portalblockz.portalbot.command.*;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by portalBlock on 9/19/2014.
 */
public class MCP extends BasicCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender){
            sender.sendMessage("Not implemented!");
            return;
        }
        String host, port;
        if(args.length >= 1){
            String[] temp = args[0].split(":");
            if(temp.length > 1){
                host = temp[0];
                port = temp[1];
            }else{
                host = temp[0];
                port = "25565";
            }
        }else{
            sender.sendMessage("Please include an address to ping!");
            return;
        }
        UserCommandSender ucs = (UserCommandSender) sender;
        String msg = ping(host, Integer.valueOf(port));
        if(msg == null){
            ucs.getSession().getChannel(ucs.getChannel()).say("Unable to ping server, is it down?");
            return;
        }
        ucs.getSession().getChannel(ucs.getChannel()).say("("+ucs.getName()+") "+ChatTrans.translateColorCodes(msg));
    }

    private String ping(String ip, int port){
        PingerUtil pinger = new PingerUtil();
        pinger.setAddress(new InetSocketAddress(ip, port));
        PingerUtil.StatusResponse response = null;
        try{
            response = pinger.fetchData();
        }catch (IOException ignored){

        }
        if(response == null) return null;
        String motd = response.getDescription();
        String version = response.getVersion().getName();
        String cur = response.getPlayers().getOnline()+"";
        String max = response.getPlayers().getMax()+"";
        String text = motd + ChatColor.RED+" - "+version+" - "+cur+"/"+max;
        return (text.replaceAll("null", ""));
    }
}
