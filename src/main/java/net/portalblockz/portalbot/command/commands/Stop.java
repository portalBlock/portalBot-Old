/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.command.commands;

import jerklib.util.Colors;
import net.portalblockz.portalbot.PortalBot;
import net.portalblockz.portalbot.command.BasicCommand;
import net.portalblockz.portalbot.command.CommandSender;
import net.portalblockz.portalbot.command.ConsoleCommandSender;

/**
 * Created by portalBlock on 9/1/2014.
 */
public class Stop extends BasicCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender || shouldProceed(sender)){
            sender.sendMessage("Shutting down the bot!");
            PortalBot.getInstance().stop();
        }else{
            sender.sendMessage(Colors.RED+"Sorry you are not allowed to do that!");
        }
    }
}
