package net.portalblockz.portalbot.command;

import net.portalblockz.portalbot.command.commands.*;

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
        commandMap.put("join", Join.class);
        commandMap.put("kick", Kick.class);
        commandMap.put("remember", Remember.class);
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
