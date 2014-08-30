package net.portalblock.portalbot.features;

import jerklib.events.MessageEvent;
import jerklib.events.modes.ModeAdjustment;
import jerklib.util.Colors;

import java.util.HashMap;
import java.util.List;

/**
 * Created by portalBlock on 8/30/2014.
 */
public class Remember {
    private static HashMap<String, String> memory = new HashMap<>();

    public static void add(String[] msgAr, MessageEvent me, String key, String msg){
        boolean noPerms = true;
        if(msgAr.length >= 2){
            List<ModeAdjustment> ma = me.getChannel().getUsersModes(me.getNick());
            for(ModeAdjustment a : ma){
                if(a.getMode() == 'o'||a.getMode() == 'q'){
                    StringBuilder builder = new StringBuilder();
                    for(int i = 2; i < msgAr.length; i++){
                        builder.append(msgAr[i]);
                        builder.append(" ");
                    }
                    memory.put(key.toLowerCase().trim(), builder.toString().trim());
                    noPerms = false;
                    break;
                }
            }
            if(noPerms){
                me.getSession().notice(me.getNick(), Colors.RED+"You don't have permission!");
            }
        }else{
            me.getSession().notice(me.getNick(), Colors.RED+"You need to include what you want to remember!");
        }
    }

    public static void triggerMemory(MessageEvent me, String key){
        String s = memory.get(key.toLowerCase().trim());
        if(s != null){
            me.getChannel().say(s);
        }
    }
}
