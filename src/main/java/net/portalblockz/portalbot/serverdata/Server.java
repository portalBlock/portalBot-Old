package net.portalblockz.portalbot.serverdata;

import java.util.List;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class Server {

    private String host, username, pass;
    private int port;
    private List<String> channels, staff;
    private char prefix;

    public Server(String host, String username, String pass, int port, List<String> channels, List<String> staff, char prefix) {
        this.host = host;
        this.username = username;
        this.pass = pass;
        this.port = port;
        this.channels = channels;
        this.staff = staff;
        this.prefix = prefix;
    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public int getPort() {
        return port;
    }

    public List<String> getChannels() {
        return channels;
    }

    public List<String> getStaff() {
        return staff;
    }

    public char getPrefix(){return prefix;}
}
