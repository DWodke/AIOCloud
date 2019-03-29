package de.aiocloud;

import java.io.IOException;

import org.fusesource.jansi.AnsiConsole;

import de.aiocloud.dispatcher.Dispatcher;
import de.aiocloud.logger.LoggingHandler;
import de.aiocloud.logger.LoggingType;
import de.aiocloud.worker.Worker;
import jline.console.ConsoleReader;

public class AIOCloud {

    // Declare variables
    public static LoggingHandler logginghandler;
    public static ConsoleReader reader;

    /**
     * This method create a new instance of AIOCloud-Application.
     *
     * @param args is for the start-options from the system.
     */
    public static void main(String[] args) {
        printHeader();
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
    	addShutdownHook();
    	logginghandler = new LoggingHandler();
        try {
        	AnsiConsole.systemInstall();
            reader = new ConsoleReader(System.in, System.out);
            reader.setExpandEvents(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(arg0 == null) {
        	logginghandler.print("Pls choose one of this components:", LoggingType.INFO);
        	logginghandler.print("Dispatcher: 1", LoggingType.INFO);
        	logginghandler.print("Worker: 2", LoggingType.INFO);
            quest();
        } else {

        }
    }
    /**
     * This is the method to perform the component-question.
     */
    public static void quest() {
        String component = null;
		try {
			component = reader.readLine("   AIOCloud > ");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        try {
            Integer componentnumber = Integer.parseInt(component);
            if(componentnumber == 1) {
            	logginghandler.clear();
            	reader.setPrompt("");
                try {
                    reader.resetPromptLine("", "", 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Dispatcher.startDispatcherComponent();
                return;
            } else if(componentnumber == 2) {
            	logginghandler.clear();
                reader.setPrompt("");
                try {
                    reader.resetPromptLine("", "", 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            	Worker.startWorkerComponent();
                return;
            } else {
            	logginghandler.print("Pls type a number between 1 and 2 in the console.", LoggingType.INFO);
                quest();
            }
        } catch (NumberFormatException e) {
        	logginghandler.print("Pls type a number between 1 and 2 in the console.", LoggingType.INFO);
            quest();
        }
    }
    /**
     * Prints the header of the system in the console.
     */
    public static void printHeader() {
    	System.out.println("    _    ___ ___   ____ _                 _ \n" +
                "   / \\  |_ _/ _ \\ / ___| | ___  _   _  __| |\n" +
                "  / _ \\  | | | | | |   | |/ _ \\| | | |/ _` |\n" +
                " / ___ \\ | | |_| | |___| | (_) | |_| | (_| |\n" +
                "/_/   \\_\\___\\___/ \\____|_|\\___/ \\__,_|\\__,_|\n" +
                "                        (c) by Dominik Wodke");
    }
    /**
     * Add a shutdown-hook to the system.
     */
    public static void addShutdownHook() {
    	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			public void run() {
				logginghandler.clear();
				printHeader();
				for(int i = 0; i < 5; i++) {
		            System.out.println(" ");
		        }
				logginghandler.print("Thank you for using the AIOCloud-Application! :)", LoggingType.INFO);
			}
		}));
    }
}