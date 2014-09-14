/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.command.commands;

import com.bobacadodl.imgmessage.ImageChar;
import com.bobacadodl.imgmessage.ImageMessage;
import net.portalblockz.portalbot.command.BasicCommand;
import net.portalblockz.portalbot.command.CommandSender;
import net.portalblockz.portalbot.command.ConsoleCommandSender;
import net.portalblockz.portalbot.command.UserCommandSender;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Created by portalBlock on 9/14/2014.
 */
public class Avatar extends BasicCommand {

    @Override
    public void handle(CommandSender sender, String[] args) {
        if(true){
            sender.sendMessage("Disabled because its spammy snd doesnt work :O");
            return;
        }
        if(sender instanceof ConsoleCommandSender){
            sender.sendMessage("You can't do that, sorry.");
            return;
        }
        String name = "portalBlockz";
        if(args.length < 1){
            name = args[0];
        }
        String url = String.format("https://minotar.net/avatar/%s/50.png", name);
        try{
            BufferedImage image = ImageIO.read(new URL(url));
            ImageMessage message = new ImageMessage(image, 50, ImageChar.CUSTOM.getChar());
            message.sendToChannel(((UserCommandSender)sender).getSession().getChannel(((UserCommandSender) sender).getChannel()));
        }catch (Exception e){
            sender.sendMessage("An error has occurred: "+e.getMessage());
        }
    }
}
