package de.aiocloud;

import de.aiocloud.logger.LoggingBuilder;
import de.aiocloud.logger.LoggingType;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class AIOCloud {

    // Declare variables
    public static LoggingBuilder loggingbuilder;
    public static Terminal terminal;
    public static LineReader reader;

    /**
     * This method create a new instance of AIOCloud-Application.
     *
     * @param args is for teh start-options from the system.
     */
    public static void main(String[] args) {
        System.out.println("    _    ___ ___   ____ _                 _ \n" +
                           "   / \\  |_ _/ _ \\ / ___| | ___  _   _  __| |\n" +
                           "  / _ \\  | | | | | |   | |/ _ \\| | | |/ _` |\n" +
                           " / ___ \\ | | |_| | |___| | (_) | |_| | (_| |\n" +
                           "/_/   \\_\\___\\___/ \\____|_|\\___/ \\__,_|\\__,_|\n" +
                           "                        (c) by Dominik Wodke");
        for(int i = 0; i < 5; i++) {
            System.out.println(" ");
        }
        String component = null;
        if(args.length != 0) {
            component = args[0];
        }
        if(component != null) {
            component = component.replaceAll("--component=", "");
        }
        init(component);
    }
    /**
     * This is the initial method of the system.
     */
    private static void init(String arg0) {
        loggingbuilder = new LoggingBuilder();
        try {
            terminal = TerminalBuilder.terminal();
            reader = LineReaderBuilder.builder().terminal(terminal).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(arg0 == null) {
            loggingbuilder.print("Pls choose one of this components:", LoggingType.INFO);
            loggingbuilder.print("Manager: 1", LoggingType.INFO);
            loggingbuilder.print("Worker: 2", LoggingType.INFO);
            quest();
        } else {

        }
    }
    /**
     * This is the method to perform the component-question.
     */
    public static void quest() {
        String component = reader.readLine("   AIOCloud > ");
        try {
            Integer componentnumber = Integer.parseInt(component);
            if(componentnumber == 1) {

            } else if(componentnumber == 2) {

            } else {
                loggingbuilder.print("Pls type a number between 1 and 2 in the console.", LoggingType.INFO);
                quest();
            }
        } catch (NumberFormatException e) {
            loggingbuilder.print("Pls type a number between 1 and 2 in the console.", LoggingType.INFO);
            quest();
        }
    }
}