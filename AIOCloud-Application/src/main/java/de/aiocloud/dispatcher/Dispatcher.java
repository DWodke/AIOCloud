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

		if(FileManager.load()) {

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
	/**
	 * Starts the setup of the dispatcher.
	 */
	public static void startSetup() {
		String hostaddress;
		String firstworker;
		String firstworkeraddress;
		String firstproxygroup;
		String firstgroup;
		Integer firstproxygroupramm;
		Integer firstgrupramm;
		AIOCloud.logginghandler.print("This is the setup of the dispatcher!", LoggingType.INFO);
		AIOCloud.logginghandler.print("Please fill all fields.", LoggingType.INFO);
		for(int i = 0; i < 2; i++) {
			System.out.println(" ");
		}
		AIOCloud.logginghandler.print("What is the host-address of the system? (127.0.0.1)", LoggingType.INFO);
		String line = null;
		try {
			line = AIOCloud.reader.readLine("   AIOCloud > ");
			hostaddress = line;
			AIOCloud.logginghandler.print("What is the name of the first worker? (Worker-1)", LoggingType.INFO);
			line = AIOCloud.reader.readLine("   AIOCloud > ");
			firstworker = line;
			AIOCloud.logginghandler.print("What is the address from the first worker? (127.0.0.1)", LoggingType.INFO);
			line = AIOCloud.reader.readLine("   AIOCloud > ");
			firstworkeraddress = line;
			AIOCloud.logginghandler.print("What is the name of the first proxy-group? (Proxy)", LoggingType.INFO);
			line = AIOCloud.reader.readLine("   AIOCloud > ");
			firstproxygroup = line;
			AIOCloud.logginghandler.print("What is the name of the first proxy-group? (Proxy)", LoggingType.INFO);
			line = AIOCloud.reader.readLine("   AIOCloud > ");

			firstproxygroup = line;
			AIOCloud.logginghandler.print("What is the name of the first group? (Lobby)", LoggingType.INFO);
			line = AIOCloud.reader.readLine("   AIOCloud > ");
			firstgroup = line;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}