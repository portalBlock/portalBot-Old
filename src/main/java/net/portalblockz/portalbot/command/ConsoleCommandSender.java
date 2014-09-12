/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.command;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class ConsoleCommandSender implements CommandSender{

    @Override
    public String getName() {
        return "console";
    }

    @Override
    public void sendMessage(String s) {
        System.out.println(s);
    }
}
