/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.urlshorteners;

import java.util.HashMap;

/**
 * Created by portalBlock on 11/27/2014.
 */
public abstract class URLShortener {

    private static HashMap<String, URLShortener> shorteners = new HashMap<>();

    static {
        shorteners.put("default", new GooGl());
        shorteners.put("github", new GitIo());
    }

    public static URLShortener getDefaultInstance(){
        return shorteners.get("default");
    }

    public static URLShortener getInstanceFor(String name){
        return (shorteners.get(name.toLowerCase()) == null ? getDefaultInstance() : shorteners.get(name.toLowerCase()));
    }

    public abstract String shorten(String url);

}
