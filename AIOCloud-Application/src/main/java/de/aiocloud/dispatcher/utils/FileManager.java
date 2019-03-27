package de.aiocloud.dispatcher.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

	public static File maindir;
	public static File dispatcherconfig;
	public static File groupdir;
	public static File workerdir;
	private static Gson gson;
	private static JsonParser parser;
	
	/**
	 * This is the initial method of the filemanager.
	 */
	public static Boolean load() {
		Boolean returnBoolean = false;
		gson = new GsonBuilder().setPrettyPrinting().create();
		parser = new JsonParser();
		maindir = new File("./dispatcher");
		if(!maindir.exists()) {
			returnBoolean = true;
			maindir.mkdir();
		}
		dispatcherconfig = new File("./config.json");
		if(!dispatcherconfig.exists()) {
			returnBoolean = true;
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
		return returnBoolean;
	}
	/**
	 * Create config.json from the dispatcher.
	 */
	private static void createDispatcherConfig() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(dispatcherconfig));
			JsonObject json = new JsonObject();
			json.addProperty("defaultSplitter", "-");
			json.addProperty("hostAddress", "127.0.0.1");
			json.addProperty("webPort", "8080");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
