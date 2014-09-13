/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.command.commands;

import jerklib.Channel;
import jerklib.util.Colors;
import net.portalblockz.portalbot.command.BasicCommand;
import net.portalblockz.portalbot.command.CommandSender;
import net.portalblockz.portalbot.command.UserCommandSender;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class Ban extends BasicCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {
        if(!shouldProceed(sender)){
            noPerms(sender);
            return;
        }
        if(args.length < 1){
            sender.sendMessage("Please include the person to ban! {ban <player> [reason]}");
            return;
        }
        if(sender instanceof UserCommandSender){
            UserCommandSender ucs = (UserCommandSender) sender;
            ban(ucs.getSession().getChannel(ucs.getChannel()), args[0], joinArgs(args, 1, "Banned from the channel."));
        }else{
            sender.sendMessage("Mass banning isn't that smart :P");
        }
    }

    private void ban(Channel channel, String user, String reason){
        if(channel == null) return;
        channel.kick(user, reason);
        channel.getSession().sayPrivate("chanserv", String.format("ban %s %s", channel.getName(), user));
    }

    private String joinArgs(String[] a, int i, String def){
        StringBuilder sb = new StringBuilder();
        while (i < a.length){
            sb.append(a[i]).append(" ");
            i++;
        }
        if(sb.toString().trim().length() > 0){
            return sb.toString().trim();
        }else{
            return def;
        }
    }
}
