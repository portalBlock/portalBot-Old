/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.smarts;

/**
 * Created by portalBlock on 9/14/2014.
 */
public class IRCUser {

    private int repeat, spam, caps = 0;

    public int getRepeat() {
        return repeat;
    }

    public int getSpam() {
        return spam;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public void setSpam(int spam) {
        this.spam = spam;
    }

    public int getCaps() {
        return caps;
    }

    public void setCaps(int caps) {
        this.caps = caps;
    }
}
