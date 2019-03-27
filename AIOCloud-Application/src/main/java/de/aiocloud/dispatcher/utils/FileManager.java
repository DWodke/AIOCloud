package de.aiocloud.dispatcher.utils;

import java.io.File;
import java.io.IOException;

public class FileManager {

	public static File maindir;
	public static File dispatcherconfig;
	public static File groupdir;
	public static File workerdir;
	
	/**
	 * This is the initial method of the filemanager.
	 */
	public static void load() {
		maindir = new File("./dispatcher");
		if(!maindir.exists()) {
			maindir.mkdir();
		}
		dispatcherconfig = new File("./config.json");
		if(!dispatcherconfig.exists()) {
			try {
				dispatcherconfig.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		groupdir = new File("./dispatcher/groups");
		if(!groupdir.exists()) {
			groupdir.mkdir();
		}
		workerdir = new File("./dispatcher/worker");
		if(!workerdir.exists()) {
			workerdir.mkdir();
		}
	}
}
