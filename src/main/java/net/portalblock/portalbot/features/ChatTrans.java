package net.portalblock.portalbot.features;

import jerklib.util.Colors;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by portalBlock on 6/16/2014.
 */
public class ChatTrans {



    private static final Map<Character, String> trans = new HashMap<>();

    static {
        //W.I.P. More colors will be added.
        trans.put('a', Colors.GREEN);
        trans.put('6', Colors.YELLOW);
        trans.put('0', Colors.BLACK);
        trans.put('b', Colors.BLUE);
        trans.put('l', Colors.BOLD);
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
