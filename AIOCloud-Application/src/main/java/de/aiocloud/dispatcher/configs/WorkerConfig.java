package de.aiocloud.dispatcher.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import de.aiocloud.AIOCloud;
import de.aiocloud.dispatcher.Dispatcher;
import de.aiocloud.dispatcher.utils.FileManager;
import de.aiocloud.logger.LoggingType;

import java.io.*;
import java.util.UUID;

public class WorkerConfig {

    private String name;
    private String host;
    private Integer port;
    private Integer id;
    private String authtoken;
    private String createdat;
    private File file;
    private Gson gson;

    /**
     * This is the initial method of the 'ProxyConfig' object.
     * @param file is the file to write in.
     */
    public WorkerConfig(File file) {
        this.file = file;
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
    /**
     * This method creates the file.
     */
    public void create() {
        if(this.file.exists()) {
            this.file.delete();
        } else {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer = new FileWriter(this.file);
            JsonObject json = new JsonObject();
            json.addProperty("name", this.name);
            json.addProperty("authToken", UUID.randomUUID().toString());
            json.addProperty("port", this.port);
            json.addProperty("host", this.host);
            Dispatcher.portmanager.addWorkerPort(this.port);
            json.addProperty("createdAt", this.createdat);
            writer.write(gson.toJson(json));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            AIOCloud.logginghandler.print("ERROR while create workerConfig from worker '" + this.name + "'.", LoggingType.ERROR);
        }
    }
    /**
     * This method load all data from the config file.
     */
    public Boolean load() {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            this.name = json.get("name").getAsString();
            this.port = json.get("port").getAsInt();
            this.authtoken = json.get("authToken").getAsString();
            this.host = json.get("host").getAsString();
            this.createdat = json.get("createdAt").getAsString();
            this.id = Integer.parseInt(this.name.split(FileManager.config.getDefaultSeperator())[1]);
            return true;
        } catch (Exception e) {
            AIOCloud.logginghandler.print("ERROR while load file from worker '" + this.name + "'.", LoggingType.ERROR);
            return false;
        }
    }
    /**
     * This methods are set methods.
     * @param name is the name of the proxy.
     */
    public void setName(String name) {
        this.name = name;
    }
    public void setCreatedAt(String createdat) {
        this.createdat = createdat;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setPort(Integer port) {
        this.port = port;
    }
    /**
     * This methods are get methods.
     * @return
     */
    public String getName() {
        return name;
    }
    public String getCreatedAt() {
        return createdat;
    }
    public String getHost() {
        return host;
    }
    public Integer getPort() {
        return port;
    }
    public String getAuthToken() {
        return authtoken;
    }
    public Integer getId() {
        return id;
    }
}