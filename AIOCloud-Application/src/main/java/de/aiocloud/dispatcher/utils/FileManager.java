package de.aiocloud.dispatcher.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import de.aiocloud.AIOCloud;
import de.aiocloud.dispatcher.Dispatcher;
import de.aiocloud.dispatcher.objects.Config;
import de.aiocloud.logger.LoggingType;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileManager {

	public static File maindir;
	public static File dispatcherconfig;
	public static File groupdir;
	public static File proxygroupdir;
	public static File servergroupdir;
	public static File workerdir;
	private static Gson gson;
	private static JsonParser parser;
	public static Config config;
	
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
		proxygroupdir = new File("./dispatcher/groups/proxies");
		if(!proxygroupdir.exists()) {
			proxygroupdir.mkdir();
		}
		servergroupdir = new File("./dispatcher/groups/server");
		if(!servergroupdir.exists()) {
			servergroupdir.mkdir();
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
	public static void createDispatcherConfig(String host, String minecraftversion, String serverversion, String proxyversion) {
		try {
			FileWriter writer = new FileWriter(dispatcherconfig);
			JsonObject json = new JsonObject();
			json.addProperty("defaultSeparator", "-");
			json.addProperty("hostAddress", host);
			json.addProperty("webPort", "8080");
			json.addProperty("minecraftVersion", minecraftversion);
			json.addProperty("serverVersion", serverversion);
			json.addProperty("proxyVersion", proxyversion);
			writer.write(gson.toJson(json));
			writer.flush();
			writer.close();
			loadDispatcherConfig();
			createWorker("Worker-1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Load config.json from the dispatcher.
	 */
	public static void loadDispatcherConfig() {
		try {
			JsonReader reader = new JsonReader(new FileReader(dispatcherconfig));
			JsonObject json = gson.fromJson(reader, JsonObject.class);
			config = new Config(json);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method create an worker.
	 * @param name its the name of the worker.
	 * @return boolean.
	 */
	public static Boolean createWorker(String name) {
		try {
			File workerfile = new File(workerdir.getPath() + "/" + name + ".json");
			if(!workerfile.exists()) {
				workerfile.createNewFile();
				FileWriter writer = new FileWriter(workerfile);
				JsonObject json = new JsonObject();
				json.addProperty("name", name);
				json.addProperty("id", UUID.randomUUID().toString());
				Integer port = Dispatcher.portmanager.getNextFreeWorkerPort();
				json.addProperty("port", port);
				Dispatcher.portmanager.addWorkerPort(port);
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				json.addProperty("createdAt", format.format(new Date()).toString());
				writer.write(gson.toJson(json));
				writer.flush();
				writer.close();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * This method creates a proxy-group in the dispatcher.
	 * @param name name of the group.
	 * @param worker client of group.
	 * @param memory total memory of the group.
	 * @param online minimal online server.
	 * @param maxonline maximal server online.
	 * @param dynamic is the group dynamic?
	 * @return
	 */
	public static Boolean createProxyGroup(String name, String worker, Integer memory, Integer online, Integer maxonline, Boolean dynamic) {
		try {
			File proxygroupfile = new File(proxygroupdir.getPath() + "/" + name + ".json");
			if(!proxygroupfile.exists()) {
				proxygroupfile.createNewFile();
				FileWriter writer = new FileWriter(proxygroupfile);
				JsonObject json = new JsonObject();
				json.addProperty("name", name);
				json.addProperty("worker", worker);
				Integer port = Dispatcher.portmanager.getNextFreeProxyPort();
				json.addProperty("port", port);
				Dispatcher.portmanager.addProxyPort(port);
				json.addProperty("startPort", 25565);
				json.addProperty("save_logs", true);
				json.addProperty("memory", memory);
				json.addProperty("minOnline", online);
				json.addProperty("maxOnline", maxonline);
				json.addProperty("dynamic", dynamic);
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				json.addProperty("createdAt", format.format(new Date()).toString());
				writer.write(gson.toJson(json));
				writer.flush();
				writer.close();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * This method registered all worker in the dispatcher.
	 */
	public static void registerAllWorker() {
		try {
			AIOCloud.logginghandler.print("Load all worker...", LoggingType.INFO);
			for(File file : workerdir.listFiles()) {
				if(file.getName().endsWith("json")) {
					String name = file.getName().replaceAll(".json", "");
					JsonReader reader = new JsonReader(new FileReader(file));
					JsonObject json = gson.fromJson(reader, JsonObject.class);
					Dispatcher.availableworker.put(name, new WorkerObject(config.getHostname(), json.get("port").getAsInt(), name, json.get("id").getAsString()));
					Dispatcher.portmanager.addWorkerPort(json.get("port").getAsInt());
					AIOCloud.logginghandler.print("Worker loaded [Name: " + name + ", ID: " + json.get("id").getAsString() + ", Port: " + json.get("port").getAsInt() + "]", LoggingType.INFO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
