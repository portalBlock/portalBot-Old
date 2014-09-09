package net.portalblockz.portalbot.webinterface;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jerklib.Channel;
import net.portalblockz.portalbot.PortalBot;
import net.portalblockz.portalbot.Utils;
import net.portalblockz.portalbot.serverdata.ConnectionPack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by portalBlock on 9/1/2014.
 */
public class WebIntHandler implements HttpHandler {
    private String indexContent;

    public WebIntHandler() throws Exception{
        File file = new File("index.html");
        if(!file.exists()){
            StringBuilder reply = new StringBuilder();
            reply.append("<html><head><title>portalBot</title></head><body>");
            reply.append("<h1 style=\"color:red\">portalBot IRC Bot</h1>");
            reply.append("<p>This is the default page for portalBot IRC Bot web interface... please add a page by adding index.html to your bot's root folder!</p>");
            reply.append("<br><p> %servers% </p>");
            reply.append("</body></html>");
            indexContent = reply.toString();
        }else{
            indexContent = readFile(file);
        }

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //TODO: Make this dynamic with a page, add support for multiple pages, make it actually display info!
        /*String header = "<h1 style=\"color:red\">portalBot IRC Bot</h1>";
        String text =
                "<p>Welcome this is the beginning of a web interface for portalBot, its a WIP and right now does not have anything but this message on it!</p>";*/
        String footer = "<br><center>&copy; portalBlock 2014</center>";
        String reply = indexContent;
        reply = reply.replaceAll("%servers%", getJSONServers());
        sendReply(httpExchange, reply+footer);
    }

    private void sendReply(HttpExchange httpExchange, String msg){
        sendReply(httpExchange, msg, "text/html");
    }

    private void sendReply(HttpExchange httpExchange, String response, String ctntType){
        try{
            //String response = "<html><head><title>portalBot</title></head><body>"+msg+"</body></html>";
            Map<String, String> newHeaders = new HashMap<>();
            newHeaders.put("Date", Utils.getDate());
            newHeaders.put("Server", "portalBot IRC Bot Webserver");
            newHeaders.put("Content-Length", response.length()+"");
            newHeaders.put("Content-Type", ctntType);
            for(Map.Entry<String, String> entry : newHeaders.entrySet()){
                List<String> l = new ArrayList<>();
                l.add(entry.getValue());
                httpExchange.getResponseHeaders().put(entry.getKey(), l);
            }
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.flush();
            os.close();
            httpExchange.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String readFile(File file){
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(" ");
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String s;
            while ((s = reader.readLine()) != null){
                responseBuilder.append(s);
            }
        }catch (Exception e){

        }
        return responseBuilder.toString();
    }

    private String getJSONServers(){
        /*
            Server Host
            Channels
                Channel Name
                People
         */
        JSONArray array = new JSONArray();
        for(ConnectionPack pack : PortalBot.getInstance().getConnections()){
            JSONObject server = new JSONObject();
            server.put("host", pack.getHost());
            //Make channels
            JSONArray channels = new JSONArray();
            for(Channel channel : pack.getSession().getChannels()){
                JSONObject chnl = new JSONObject();
                chnl.put("name", channel.getName());
                chnl.put("people", channel.getNicks());
                channels.put(chnl);
            }
            server.put("channels", channels);

            //Add other things?


            //Add connection to array
            array.put(server);
        }
        return array.toString();
    }
}
