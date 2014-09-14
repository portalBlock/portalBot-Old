/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot;

import jerklib.util.Colors;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by portalBlock on 6/16/2014.
 * With help from @Daboross
 */
public class ChatTrans {
    private static final Map<Character, String> trans = new HashMap<>();

    static {
        trans.put('a', Colors.GREEN);
        trans.put('b', Colors.CYAN);
        trans.put('c', Colors.RED);
        trans.put('d', Colors.MAGENTA);
        trans.put('e', Colors.YELLOW);
        trans.put('f', Colors.WHITE);
        trans.put('r', Colors.NORMAL);
        trans.put('n', Colors.UNDERLINE);
        trans.put('0', Colors.BLACK);
        trans.put('1', Colors.DARK_BLUE);
        trans.put('2', Colors.DARK_GRAY);
        trans.put('3', Colors.TEAL);
        trans.put('4', Colors.RED);
        trans.put('5', Colors.PURPLE);
        trans.put('6', Colors.OLIVE);
        trans.put('7', Colors.LIGHT_GRAY);
        trans.put('8', Colors.DARK_GRAY);
        trans.put('9', Colors.BLUE);

    }

    public static String translateColorCodes(String textToTranslate) {
        char[] b = textToTranslate.trim().toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == '\u00A7' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i+1]) > -1) {
                builder.append(trans.get(b[i+1]));
            } else {
                if(b[i-1] != '\u00A7'){
                    builder.append(b[i]);
                }
            }
        }
        return builder.append(b[b.length-1]).toString().replaceAll("[\\s\\s]+", " ");
    }

}

