package net.portalblock.portalbot.features.management;

import jerklib.events.IRCEvent;
import jerklib.events.MessageEvent;
import jerklib.events.modes.ModeAdjustment;
import jerklib.util.Colors;

import java.util.List;

/**
 * Created by portalBlock on 3/1/14.
 */
public class OP {
    public static void op(MessageEvent me, IRCEvent e){
        String[] msgAr = me.getMessage().replaceFirst("`", "").split(" ");
        if(msgAr[0].equalsIgnoreCase("op")){
            boolean notOp = true;
            boolean noPerms = true;
            if(msgAr.length >= 2){
                List<ModeAdjustment> ma = me.getChannel().getUsersModes(me.getNick());
                for(ModeAdjustment a : ma){
                    if(notOp == true){
                        if(a.getMode() == 'o'||a.getMode() == 'q'){
                            me.getChannel().op(msgAr[1]);
                            notOp = false;
                            noPerms = false;
                        }
                    }
                }
                if(noPerms){
                    e.getSession().notice(me.getNick(), Colors.RED+"You don't have permission!");
                }
            }else{
                e.getSession().notice(me.getNick(), Colors.RED+"You need to include who you want to be opped!");
            }
        }
    }
}
