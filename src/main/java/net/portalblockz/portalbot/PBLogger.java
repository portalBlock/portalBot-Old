package net.portalblockz.portalbot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by portalBlock on 8/31/2014.
 */
public class PBLogger extends ByteArrayOutputStream {

    private final String separator = System.getProperty("line.separator");
    private final PortalBot bot;

    public PBLogger(PortalBot bot){
        this.bot = bot;
    }


    @Override
    public void flush() throws IOException {
        super.flush();
        String record = this.toString();
        super.reset();
        if ((record.length() > 0) && (!record.equals(separator))) {
            bot.print(record);
        }
    }
}
