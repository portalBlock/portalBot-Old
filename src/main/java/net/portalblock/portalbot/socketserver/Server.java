package net.portalblock.portalbot.socketserver;

import net.portalblock.portalbot.PortalBot;
import net.portalblock.portalbot.features.consolecommands.Join;
import net.portalblock.portalbot.features.consolecommands.Kick;
import net.portalblock.portalbot.features.consolecommands.Stop;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by portalBlock on 3/5/14.
 */
public class Server {

    static ServerSocket providerSocket;
    static Socket connection = null;
    static ObjectOutputStream out;
    static ObjectInputStream in;
    static String message;

    public void run(){
        try{
            providerSocket = new ServerSocket(9090);
            print("Waiting for connection....");
            connection = providerSocket.accept();
            print("Connection get: "+connection.getInetAddress());
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            //sendMessage("Connection successful");
            //Scanner input = new Scanner(System.in);
            Thread getter = new Thread(new Runnable() {
                @Override
                public void run() {
                    do{
                        try{
                            message = (String)in.readObject();
                            //System.out.println("client>" + message);
                            String[] cmdAr = message.split(" ");
                            print(cmdAr[0]);
                            switch (cmdAr[0]){
                                case "stop": Stop.stop(); return;
                                case "say":
                                    String cmdArgs = "";
                                    for(int i = 1; i <= cmdAr.length-1; i++){
                                        cmdArgs += cmdAr[i]+ " ";
                                    }
                                    PortalBot.say(cmdArgs);//Calls method to speak in all channels
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
                                        Join.join(cmdAr[1]);
                                        break;
                                    }
                                default: print("Command not found."); break;
                            }
                        }
                        catch(ClassNotFoundException classnot){
                            print("Class not found!");
                        }catch (IOException e){

                        }
                    }while(true);
                }
            });
            getter.start();
        }catch (IOException e){
        }

    }
    public void print(String msg){
        System.out.println(msg);
    }

    public void sendMessage(String msg){
        try{
            out.writeObject(msg);
            out.flush();
            print("server>" + msg);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
