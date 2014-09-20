/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.command.commands;

import net.portalblockz.portalbot.command.BasicCommand;
import net.portalblockz.portalbot.command.CommandSender;
import net.portalblockz.portalbot.command.ConsoleCommandSender;
import net.portalblockz.portalbot.smarts.SmartListener;

/**
 * Created by portalBlock on 9/19/2014.
 */
public class ResetUser extends BasicCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender || shouldProceed(sender)){
            if(args.length < 1){
                sender.sendMessage("Please include a user to reset!");
                return;
            }
            SmartListener.flushUser(args[0]);
            sender.sendMessage("Done!");
        }else{
            noPerms(sender);
        }
    }
}
