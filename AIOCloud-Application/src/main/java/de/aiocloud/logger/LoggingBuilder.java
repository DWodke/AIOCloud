package de.aiocloud.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingBuilder {

    private String message;
    private LoggingType type;
    private SimpleDateFormat dateformat;

    /**
     * This is the initial method of the 'LoggingBuilder' object.
     * @param type
     * @param message
     */
    public LoggingBuilder(LoggingType type, String message) {
        this.type = type;
        this.message = message;
        this.dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    /**
     * Prints the @param(message) and the @param(type) in the console.
     */
    public void print() {
        System.out.println("[" + this.dateformat.format(new Date()).toString() + "] [" + this.type + "] - " + this.message);
        System.out.print("  AIOCloud > ");
    }
    /**
     * GET method
     * @return the message string.
     */
    public String getMessage() {
        return message;
    }
    /**
     * GET method
     * @return the message type.
     */
    public LoggingType getType() {
        return type;
    }
}