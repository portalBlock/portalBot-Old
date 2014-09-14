/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

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
        commandMap.put("potato", Potato.class);
        commandMap.put("op", Op.class);
        commandMap.put("avatar", Avatar.class);
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
