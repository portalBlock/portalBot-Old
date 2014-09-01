package net.portalblockz.portalbot.git;

import com.sun.net.httpserver.HttpExchange;

/**
 * Created by portalBlock on 8/30/2014.
 */
public abstract class IGitEvent {

    public abstract void handle(HttpExchange httpExchange);

}
