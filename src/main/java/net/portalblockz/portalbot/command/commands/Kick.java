/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.command.commands;

import jerklib.util.Colors;
import net.portalblockz.portalbot.command.BasicCommand;
import net.portalblockz.portalbot.command.CommandSender;
import net.portalblockz.portalbot.command.ConsoleCommandSender;
import net.portalblockz.portalbot.command.UserCommandSender;

/**
 * Created by portalBlock on 9/1/2014.
 */
public class Kick extends BasicCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender){
            sender.sendMessage("You can't mass kick!");
            return;
        }
        if(shouldProceed(sender)){
            if(args.length < 1){
                sender.sendMessage("Please include the person to kick! {kick <player> [reason]}");
            }
            String reason = "Kicked from the channel.";
            if(args.length > 1){
                StringBuilder builder = new StringBuilder();
                for(int i = 1; i < args.length; i++){
                    builder.append(args[i]).append(" ");
                }
                reason = builder.toString().trim();
            }
            UserCommandSender ucs = (UserCommandSender) sender;
            ucs.getSession().getChannel(ucs.getChannel()).kick(args[0], reason);
        }else{
            noPerms(sender);
        }
    }
}
