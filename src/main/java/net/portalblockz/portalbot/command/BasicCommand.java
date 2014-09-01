package net.portalblockz.portalbot.command;

/**
 * Created by portalBlock on 8/31/2014.
 */
public abstract class BasicCommand {

    public abstract void handle(CommandSender sender, String[] args);

    public final boolean shouldProceed(CommandSender sender){
        boolean proceed = true;
        if(sender instanceof UserCommandSender){
            UserCommandSender ucs = (UserCommandSender) sender;
            if(!ucs.isStaff(ucs.getServer())){
                proceed = false;
            }
        }
        return proceed;
    }

}
