package de.aiocloud.dispatcher;

import java.io.IOException;

import de.aiocloud.AIOCloud;
import de.aiocloud.dispatcher.objects.CommandManager;
import de.aiocloud.dispatcher.utils.FileManager;
import de.aiocloud.logger.LoggingType;

public class Dispatcher {
	
	public static CommandManager commandmanager;
	
	/**
	 * This is the initial method of the dispatcher.
	 */
	public static void startDispatcherComponent() {
		AIOCloud.printHeader();
		for(int i = 0; i < 5; i++) {
			System.out.println(" ");
		}
		AIOCloud.logginghandler.print("Told dispatcher to load all components...", LoggingType.INFO);
		for(int i = 0; i < 2; i++) {
			System.out.println(" ");
		}
		FileManager.load();
		commandmanager = new CommandManager();
		register();
		startCommandReading();
	}
	/**
	 * This method start to reading the commandline.
	 */
	private static void startCommandReading() {
		do {
			String commandline = null;
			try {
				commandline = AIOCloud.reader.readLine("   AIOCloud > ");
				if(commandmanager.getMap().containsKey(commandline.split(" ")[0])) {
					for(String key : commandmanager.getMap().keySet()) {
						if(commandline.toLowerCase().startsWith(key.toLowerCase())) {
							commandmanager.getMap().get(key).execute(commandline.split(" "));
							break;
						}
					}
				} else {
					AIOCloud.logginghandler.print("Use 'help' for more informations.", LoggingType.INFO);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} while(true);
	}
	/**
	 * This method registered all commands and listeners.
	 */
	private static void register() {
		
	}
}
