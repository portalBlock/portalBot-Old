package net.portalblockz.portalbot.serverdata;

import jerklib.ConnectionManager;
import jerklib.Session;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class ConnectionPack {

    private Session session;
    private ConnectionManager connectionManager;

    public ConnectionPack(ConnectionManager connectionManager, Session session) {
        this.connectionManager = connectionManager;
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }
}
