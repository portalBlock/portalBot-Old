/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.command.commands;

import jerklib.util.Colors;
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
        UserCommandSender ucs = (UserCommandSender) sender;
        if(args.length < 1){
            ucs.getSession().notice(ucs.getName(), Colors.RED+"Please include the address to ping!");
            return;
        }

        String[] set = args[0].split(":");

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
            String text = "("+ucs.getName()+") "+ChatTrans.translateColorCodes(motd)+Colors.RED+" - "+version+" - "+cur+"/"+max;
            ucs.getSession().getChannel(ucs.getChannel()).say(text.replaceAll("null", ""));
        }catch (IOException e){
            e.printStackTrace();
            ucs.getSession().getChannel(ucs.getChannel()).say(Colors.RED+"Unable to ping server!");
        }
    }
}
