package net.portalblockz.portalbot.command.commands;

import jerklib.events.MessageEvent;
import jerklib.util.Colors;
import net.portalblockz.portalbot.command.BasicCommand;
import net.portalblockz.portalbot.command.CommandSender;
import net.portalblockz.portalbot.command.ConsoleCommandSender;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by portalBlock on 9/2/2014.
 */
public class Remember extends BasicCommand {
    private static Map<String, String> memory = new HashMap<>();

    @Override
    public void handle(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender || shouldProceed(sender)) {
            if (args.length < 2) {
                sender.sendMessage("Please include the key and item to remember! {remember <key> <value>");
                return;
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                builder.append(args[i]).append(" ");
            }
            memory.put(args[0].toLowerCase(), builder.toString().trim());
        }else{
            sender.sendMessage(Colors.RED+"Sorry you are not allowed to do that!");
        }
    }

    public static void triggerMemory(String s, MessageEvent e){
        String mem = memory.get(s.toLowerCase());
        if(mem != null){
            e.getChannel().say(mem);
        }
    }
}
