package net.portalblock.portalbot.features.consolecommands;

import net.portalblock.portalbot.PortalBot;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;


/**
 * Created by portalBlock on 3/4/14.
 */
public class Stop {

    public static void stop() throws IOException{
        PortalBot.running = false;
        try{
            AnsiConsole.systemUninstall();
            PortalBot.getConsole().getTerminal().restore();
        }catch (Exception e){

        }
        PortalBot.print("Stopping the bot");
        System.exit(0);
    }
}
