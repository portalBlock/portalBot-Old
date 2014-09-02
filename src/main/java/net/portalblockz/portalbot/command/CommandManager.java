package net.portalblockz.portalbot.command;

import net.portalblockz.portalbot.command.commands.Ban;
import net.portalblockz.portalbot.command.commands.Cake;
import net.portalblockz.portalbot.command.commands.Stop;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class CommandManager {
    private static Map<String, Class<? extends BasicCommand>> commandMap = new HashMap<>();

    static {
        commandMap.put("ban", Ban.class);
        commandMap.put("cake", Cake.class);
        commandMap.put("stop", Stop.class);
    }

    public static void handle(CommandSender sender, String command, String[] args){
        Class<? extends BasicCommand> c = commandMap.get(command);
        if(c != null){
            try{
                c.newInstance().handle(sender, args);
            }catch (Exception e){

            }
        }
    }

}
