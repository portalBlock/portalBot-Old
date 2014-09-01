package net.portalblockz.portalbot.command.commands;

import net.portalblockz.portalbot.command.BasicCommand;
import net.portalblockz.portalbot.command.CommandSender;
import net.portalblockz.portalbot.command.ConsoleCommandSender;
import net.portalblockz.portalbot.command.UserCommandSender;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class Cake extends BasicCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender){
            sender.sendMessage("You cant do that!");
            return;
        }
        if(args.length < 1){
            sender.sendMessage("Please include the person to give a cake to!");
            return;
        }
        UserCommandSender ucs = (UserCommandSender)sender;
        ucs.getSession().getChannel(ucs.getChannel()).action(String.format("gives %s a cake", args[0]));
    }
}
