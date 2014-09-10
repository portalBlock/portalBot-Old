package net.portalblockz.portalbot.command.commands;

import net.portalblockz.portalbot.command.BasicCommand;
import net.portalblockz.portalbot.command.CommandSender;
import net.portalblockz.portalbot.command.UserCommandSender;

/**
 * Created by portalBlock on 9/9/2014.
 */
public class Potato extends BasicCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {
        if(sender instanceof UserCommandSender){
            UserCommandSender ucs = (UserCommandSender) sender;
            ucs.getSession().getChannel(ucs.getChannel()).action("gives Wferr a warm fluffy potato!");
        }
    }
}
