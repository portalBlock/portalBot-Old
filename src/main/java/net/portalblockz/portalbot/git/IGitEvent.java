/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.git;

/**
 * Created by portalBlock on 8/30/2014.
 */
public abstract class IGitEvent {

    public abstract void handle(String body);

}
