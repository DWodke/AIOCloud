package de.aiocloud.dispatcher;

import java.io.IOException;
import java.util.HashMap;

import de.aiocloud.AIOCloud;
import de.aiocloud.dispatcher.objects.*;
import de.aiocloud.dispatcher.utils.FileManager;
import de.aiocloud.logger.LoggingType;

public class Dispatcher {
	
	public static CommandManager commandmanager;
	public static PortManager portmanager;
	public static HashMap<String, WorkerObject> availableworker;
	public static HashMap<String, ProxyGroupObject> proxygroups;
	
	/**
	 * This is the initial method of the dispatcher.
	 */
	public static void startDispatcherComponent() {
		portmanager = new PortManager();
		proxygroups = new HashMap<String, ProxyGroupObject>();
		if(FileManager.load()) {
			new DispatcherSetup();
		} else {
			FileManager.loadDispatcherConfig();
		}
		AIOCloud.printHeader();
		for(int i = 0; i < 5; i++) {
			System.out.println(" ");
		}
		AIOCloud.logginghandler.print("Told dispatcher to load all components...", LoggingType.INFO);
		for(int i = 0; i < 2; i++) {
			System.out.println(" ");
		}
		commandmanager = new CommandManager();
		availableworker = new HashMap<String, WorkerObject>();
		register();
		FileManager.registerAllWorker();
		startCommandReading();
	}
	/**
	 * This method start to reading the commandline.
	 */
	private static void startCommandReading() {
		String commandline;
		try {
			while (true) {
				AIOCloud.reader.setPrompt("");
				AIOCloud.reader.resetPromptLine("", "", 0);
				while ((commandline = AIOCloud.reader.readLine()) != null && commandline.length() != 0) {
					if(commandmanager.getMap().containsKey(commandline.split(" ")[0])) {
						for(String key : commandmanager.getMap().keySet()) {
							if(commandline.toLowerCase().startsWith(key.toLowerCase())) {
								commandmanager.getMap().get(key).execute(commandline.split(" "));
								continue;
							}
						}
					} else {
						AIOCloud.logginghandler.print("Use 'help' for more informations.", LoggingType.INFO);
					}
				}
			}
		} catch (Exception e) {

		}
	}
	/**
	 * This method registered all commands and listeners.
	 */
	private static void register() {
		
	}
}