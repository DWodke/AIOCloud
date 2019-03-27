package de.aiocloud.logger;

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
        System.out.println("[" + this.dateformat.format(new Date()).toString() + "] [" + type + "]: " + message);
    }
    /**
     * Clear the system console.
     */
    public void clear() {
    	for(int i = 0; i < 100; i++) {
    		System.out.println(" ");
    	}
    }
}