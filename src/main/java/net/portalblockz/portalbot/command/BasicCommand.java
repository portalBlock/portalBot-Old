/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.command;

import jerklib.util.Colors;

/**
 * Created by portalBlock on 8/31/2014.
 */
public abstract class BasicCommand {

    public abstract void handle(CommandSender sender, String[] args);

    public static boolean shouldProceed(CommandSender sender){
        boolean proceed = true;
        if(sender instanceof UserCommandSender){
            UserCommandSender ucs = (UserCommandSender) sender;
            if(!ucs.isStaff(ucs.getServer())){
                proceed = false;
            }
        }
        return proceed;
    }

    public final void noPerms(CommandSender sender){
        if(sender instanceof ConsoleCommandSender){
            sender.sendMessage("Sorry you are not allowed to do that!");
        }else{
            sender.sendMessage(Colors.RED+"Sorry you are not allowed to do that!");
        }
    }

}
