/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

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
