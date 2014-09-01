package net.portalblockz.portalbot.command;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class ConsoleCommandSender implements CommandSender{

    @Override
    public String getName() {
        return "console";
    }

    @Override
    public void sendMessage(String s) {
        System.out.println(s);
    }
}
