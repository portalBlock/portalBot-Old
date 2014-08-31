package net.portalblockz.portalbot;

import jerklib.ConnectionManager;
import jerklib.Profile;
import jerklib.Session;
import jline.UnsupportedTerminal;
import jline.console.ConsoleReader;
import net.portalblockz.portalbot.serverdata.ConnectionPack;
import net.portalblockz.portalbot.serverdata.JSONConfigManager;
import net.portalblockz.portalbot.serverdata.Server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class PortalBot{

    private ConsoleReader consoleReader;
    private JSONConfigManager configManager;
    private List<ConnectionPack> connections = new ArrayList<>();

    public static void main(String[] args){
        new PortalBot();
    }

    public PortalBot(){
        try{
            /*consoleReader = new ConsoleReader();
            consoleReader.setExpandEvents(false);
            System.setOut(new PrintStream(new PBLogger(this), true));
            if(consoleReader.getTerminal() instanceof UnsupportedTerminal){
                print("Your system does not support JLine, this program will still function as normal.");
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
        configManager = new JSONConfigManager(new File("config.json"));
        for(Server server : configManager.getServers()){
            ConnectionManager manager = new ConnectionManager(new Profile(server.getUsername()));
            Session session = manager.requestConnection(server.getHost(), server.getPort());
            ConnectionPack pack = new ConnectionPack(manager, session);
            connections.add(pack);
            EventListener listener = new EventListener(pack, server);
            session.addIRCEventListener(listener);

        }
    }

    public void print(String s){
        System.out.println(s);
        /*try{
            consoleReader.println(s);
            consoleReader.flush();
        }catch (IOException e){
            e.printStackTrace();
        }*/
    }

}
