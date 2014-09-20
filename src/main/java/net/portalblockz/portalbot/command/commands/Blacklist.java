/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.command.commands;

import net.portalblockz.portalbot.command.BasicCommand;
import net.portalblockz.portalbot.command.CommandSender;
import net.portalblockz.portalbot.command.UserCommandSender;
import net.portalblockz.portalbot.serverdata.JSONConfigManager;

/**
 * Created by portalBlock on 9/20/2014.
 */
public class Blacklist extends BasicCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {
        if(sender instanceof UserCommandSender){
            sender.sendMessage("This can only be done from console!");
            return;
        }
        if(args.length < 1){
            sender.sendMessage("Please include a word to blacklist!");
            return;
        }
        JSONConfigManager.getInstance().addBlacklistWord(args[0]);
        sender.sendMessage(args[0].toLowerCase()+" has been temporarily added to the blacklist! To add it permanently please add it in config.json.");
    }
}
