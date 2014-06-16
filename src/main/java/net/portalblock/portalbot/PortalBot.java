package net.portalblock.portalbot;

import jerklib.Channel;
import jerklib.ConnectionManager;
import jerklib.Profile;
import jerklib.Session;
import jline.UnsupportedTerminal;
import jline.console.ConsoleReader;
import net.portalblock.portalbot.features.consolecommands.Join;
import net.portalblock.portalbot.features.consolecommands.Kick;
import net.portalblock.portalbot.features.consolecommands.Stop;
import org.fusesource.jansi.AnsiConsole;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by portalBlock on 1/7/14.
 */
//rand.nextInt(max-min+1)+min

//TODO: Add a .yml parser, Add user permissions for bot management
//TODO: Add more features(Such as: ping)
public class PortalBot {
    private static ConnectionManager manager;
    public static Session session;
    private static String[] login;
    static ConsoleReader consoleReader;
    public static boolean running;

    public static ConsoleReader getConsole(){
        return consoleReader;
    }

    public static void main(String[] args) throws IOException{
        AnsiConsole.systemInstall();
        consoleReader = new ConsoleReader();
        consoleReader.setExpandEvents(false);
        if(consoleReader.getTerminal() instanceof UnsupportedTerminal){
            System.out.println("Your system does not support JLine, this program will still function as normal.");
        }
        //loadPlugins();
        getLogin();//Get the login info from the login.txt file.
        portalBot();//Starts the bot, statically if I may add.
        String cmd;
        print("Bot started :D");
        Scanner input = new Scanner(System.in);
        do{//Starts the loop for commands from console.
            cmd = consoleReader.readLine(">");
            if(cmd != null){
                String cmdAr[] = cmd.split(" ");

                switch (cmdAr[0]){
                    case "stop": Stop.stop(); return;
                    case "say":
                        String cmdArgs = "";
                        for(int i = 1; i <= cmdAr.length-1; i++){
                            cmdArgs += cmdAr[i]+ " ";
                        }
                        say(cmdArgs);//Calls method to speak in all channels
                        break;
                    case "kick":
                        if(cmdAr.length < 2){
                            print("Please include the person to kick.");
                            break;
                        }else{
                            Kick.kick(cmdAr[1]);
                            break;
                        }
                    case "join":
                        if(cmdAr.length < 2){
                            print("Please include the channel to join.");
                            break;
                        }else{
                            Join.join(cmdAr[1]) ;
                            break;
                        }
                    default: print("Command not found."); break;
                }
            }else{
                print("null cmd");
            }

        }while(running);//Makes the program run until stop methods are called.
    }

    public static void print(String print){
        try{
            getConsole().println(print);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void portalBot(){//Makes connection to IRC servers and registers main listener
        manager = new ConnectionManager(new Profile(login[1]));
        session = manager.requestConnection(login[0]);
        session.addIRCEventListener(new EventListner(login));
        running = true;
    }

    public static void say(String args){//Method to speak in all channels
        for(Channel chan : session.getChannels()){//Starts loop to get all channels the bot is in.
            session.sayChannel(chan, args);//Speaks in the current loop channel
        }
    }
    public static void getLogin(){//Retrieves login information from file.
        String loginInfo = null;
        File file = new File("login.txt");//Makes the file object.
        try{
            Scanner s = new Scanner(file);
            s.useDelimiter("\\z");
            loginInfo = s.next();
            s.close();
        }catch (FileNotFoundException e){
            System.out.println("Login information not found.");//Throws file not found error
            //System.exit(1);
            try{
                file.createNewFile();//Trys to create file
                System.out.println("login.txt file created please add login info to the file.");//Tells the user the file is made
                System.out.println("LOGIN FORMAT: <host>|<nick>|<pass>|<#channel>");//Gives the format to the user
                System.exit(0);//Ends the program with exit status 0
            }catch (IOException ex){
                System.out.println("Unable to create login.txt");//Throws error that the file can not be created
                System.exit(0);//Ends the program with exit status 0
            }
        }
        if(loginInfo != null){//Checks if loginInfo is null
            login = loginInfo.split("\\|");//Splits info into usable String arrays

        }
    }

    public static void loadPlugins(){
        List<String> classes = new ArrayList();
        File path = new File("plugins");
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file){
                return file.getName().toLowerCase().endsWith(".jar");
            }
        };
        for(File plugin : path.listFiles()){
            if(plugin.getName().endsWith(".jar")){
                try{
                    URLClassLoader cl = new URLClassLoader(new URL[]{plugin.toURL()});

                }catch (MalformedURLException e){
                    e.printStackTrace();
                    continue;
                }catch (IOException e){
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

   /* public static List getClasseNames(String jarName) {
        ArrayList classes = new ArrayList();
        System.out.println("Jar " + jarName );
        try {
            JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
            JarEntry jarEntry;

            while (true) {
                jarEntry = jarFile.getNextJarEntry();
                if (jarEntry == null) {
                    break;
                }
                if (jarEntry.getName().endsWith(".class")) {
                        System.out.println("Found " + jarEntry.getName().replaceAll("/", "\\."));
                    classes.add(jarEntry.getName().replaceAll("/", "\\."));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    public static List getClasseNamesInPackage
            (String jarName, String packageName){
        ArrayList classes = new ArrayList ();

        packageName = packageName.replaceAll("\\." , "/");
        try{
            JarInputStream jarFile = new JarInputStream
                    (new FileInputStream (jarName));
            JarEntry jarEntry;

            while(true) {
                jarEntry=jarFile.getNextJarEntry ();
                if(jarEntry == null){
                    break;
                }
                if((jarEntry.getName ().startsWith (packageName)) &&
                        (jarEntry.getName ().endsWith (".class")) ) {
                    classes.add (jarEntry.getName().replaceAll("/", "\\."));
                }
            }
        }
        catch( Exception e){
            e.printStackTrace ();
        }
        return classes;
    }*/
}