package net.portalblock.portalbot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by portalBlock on 6/18/2014.
 */
public class PBLogger extends ByteArrayOutputStream {
    private final String separator = System.getProperty("line.separator");
    @Override
    public void flush() throws IOException {
        super.flush();
        String record = this.toString();
        super.reset();
        if ((record.length() > 0) && (!record.equals(separator))) {
            PortalBot.print(record);
        }
    }
}
