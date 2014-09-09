package net.portalblockz.portalbot.serverdata;

import jerklib.ConnectionManager;
import jerklib.Session;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class ConnectionPack {

    private Session session;
    private ConnectionManager connectionManager;
    private String host;
    public ConnectionPack(ConnectionManager connectionManager, Session session, String host) {
        this.connectionManager = connectionManager;
        this.session = session;
        this.host = host;
    }

    public Session getSession() {
        return session;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public String getHost() {
        return host;
    }
}
