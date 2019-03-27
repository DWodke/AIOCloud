package de.aiocloud.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingBuilder {

    private SimpleDateFormat dateformat;

    /**
     * This is the initial method of the 'LoggingBuilder' object.
     */
    public LoggingBuilder() {
        this.dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    /**
     * Prints the @param(message) and the @param(type) in the console.
     */
    public void print(String message, LoggingType type) {
        System.out.println("[" + this.dateformat.format(new Date()).toString() + "] [" + type + "]: " + message);
    }
}