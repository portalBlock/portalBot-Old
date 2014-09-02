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
            sender.sendMessage(Colors.RED+"Sorry you are not allowed to do that!");
        }
    }
}
