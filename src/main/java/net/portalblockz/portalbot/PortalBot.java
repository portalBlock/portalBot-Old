package net.portalblockz.portalbot;

import com.sun.net.httpserver.HttpServer;
import jerklib.Channel;
import jerklib.ConnectionManager;
import jerklib.Profile;
import jerklib.Session;
import jline.UnsupportedTerminal;
import jline.console.ConsoleReader;
import net.portalblockz.portalbot.command.CommandManager;
import net.portalblockz.portalbot.command.ConsoleCommandSender;
import net.portalblockz.portalbot.git.GitHubHandler;
import net.portalblockz.portalbot.serverdata.ConnectionPack;
import net.portalblockz.portalbot.serverdata.JSONConfigManager;
import net.portalblockz.portalbot.serverdata.Server;
import net.portalblockz.portalbot.webinterface.WebIntHandler;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class PortalBot{

    //private ConsoleReader consoleReader;
    private JSONConfigManager configManager;
    private List<ConnectionPack> connections = new ArrayList<>();
    private static PortalBot instance;
    //private boolean running;

    public static PortalBot getInstance() {
        return instance;
    }

    public static void main(String[] args){
        new PortalBot();
    }

    public PortalBot(){
        instance = this;
        //Load JLine
        /*try{
            consoleReader = new ConsoleReader();
            consoleReader.setExpandEvents(false);
            System.setOut(new PrintStream(new PBLogger(this), true));
            if(consoleReader.getTerminal() instanceof UnsupportedTerminal){
                print("Your system does not support JLine, this program will still function as normal.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
        //Load config
        configManager = new JSONConfigManager(new File("config.json"));

        //Connect to servers
        for(Server server : configManager.getServers()){
            ConnectionManager manager = new ConnectionManager(new Profile(server.getUsername()));
            Session session = manager.requestConnection(server.getHost(), server.getPort());
            ConnectionPack pack = new ConnectionPack(manager, session);
            connections.add(pack);
            EventListener listener = new EventListener(pack, server);
            session.addIRCEventListener(listener);

        }

        //Start github hook and web interface
        try{
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(5000), 0);
            httpServer.createContext("/githubapi", new GitHubHandler());
            httpServer.createContext("/", new WebIntHandler());
            httpServer.setExecutor(null);
            httpServer.start();
            print("GitHub Hook server running on port 5000");
            print("Set hook to use http://<server_address>:5000/githubapi");
        }catch (IOException e){
            e.printStackTrace();
        }

        //Set running to true for input readers
        /*running = true;*/

        //Start taking input in the main thread.
        /*String input;
        do{
            try{
                input = consoleReader.readLine(">");
                String[] total = input.split(" ");
                String command = total[0];
                String[] newArgs = new String[total.length-1];
                if(total.length > 1){
                    for(int i = 1; i < total.length; i++){
                        newArgs[i-1] = total[i];
                    }
                }
                CommandManager.handle(new ConsoleCommandSender(), command, newArgs);
            }catch (IOException e){
                e.printStackTrace();
            }
        }while (running);*/
    }

    public void globalSpeak(String s){
        print("ME: "+s);
        for(ConnectionPack pack : connections){
            for(Channel channel : pack.getSession().getChannels()){
                channel.say(s);
            }
        }
    }

    public void sayInChannels(String s, List<String> channels){
        if(channels == null) return;
        for(ConnectionPack pack : connections){
            for(Channel channel : pack.getSession().getChannels()){
                if(channels.contains(channel.getName().toLowerCase())){
                    channel.say(s);
                }
            }
        }
    }

    public void stop(){
        System.out.println("Leaving IRC channels...");
        for(ConnectionPack pack : connections){
            for(Channel channel : pack.getSession().getChannels()){
                channel.part("Shutting down...");
            }
        }
        try{
            /*System.setOut(System.out);
            consoleReader.getTerminal().restore();*/
        }catch (Exception e){
            e.printStackTrace();
        }
        //running = false;
        System.exit(0);
    }

    public void print(String s){
        System.out.println(s);
        /*try{
            consoleReader.println(s);
            //consoleReader.flush();
        }catch (IOException e){
            e.printStackTrace();
        }*/
    }

}
