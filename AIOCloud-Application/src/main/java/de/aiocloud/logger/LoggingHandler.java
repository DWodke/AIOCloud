package de.aiocloud.logger;

import de.aiocloud.AIOCloud;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingHandler {

    private SimpleDateFormat dateformat;

    /**
     * This is the initial method of the 'LoggingBuilder' object.
     */
    public LoggingHandler() {
        this.dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    /**
     * Prints the @param(message) and the @param(type) in the console.
     */
    public void print(String message, LoggingType type) {
        try {
            AIOCloud.reader.println("[" + this.dateformat.format(new Date()).toString() + "] [" + type + "]: " + message);
            AIOCloud.reader.drawLine();
            AIOCloud.reader.flush();
        } catch (Exception e) {
        }
    }
    /**
     * Clear the system console.
     */
    public void clear() {
        try {
            for(int i = 0; i < 100; i++) {
                AIOCloud.reader.println(" ");
                AIOCloud.reader.flush();
            }
        } catch (Exception e) {
        }
    }
}