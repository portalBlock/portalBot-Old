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
