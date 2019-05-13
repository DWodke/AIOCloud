package de.aiocloud.dispatcher.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import de.aiocloud.AIOCloud;
import de.aiocloud.dispatcher.Dispatcher;
import de.aiocloud.dispatcher.utils.FileManager;
import de.aiocloud.library.json.JsonArrayParser;
import de.aiocloud.logger.LoggingType;

import java.io.*;

public class WorkerConfig {

    private String name;
    private String host;
    private Integer port;
    private Integer id;
    private String authtoken;
    private String createdat;
    private File file;
    private static Gson gson;

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
            json.addProperty("authToken", this.authtoken);
            json.addProperty("port", this.port);
            json.addProperty("host", this.host);
            Dispatcher.portmanager.addWorkerPort(this.port);
            json.addProperty("createdAt", this.createdat);
            writer.write(gson.toJson(json));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            AIOCloud.logginghandler.print("ERROR while create proxyConfig from group '" + this.name + "'.", LoggingType.ERROR);
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
            AIOCloud.logginghandler.print("ERROR while load file from group '" + this.name + "'.", LoggingType.ERROR);
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
    public void setDynamic(Boolean dynamic) {
        this.dynamic = dynamic;
    }
    public void setMaxOnline(Integer maxonline) {
        this.maxonline = maxonline;
    }
    public void setMaintenance(Boolean maintenance) {
        this.maintenance = maintenance;
    }
    public void setMemory(Integer memory) {
        this.memory = memory;
    }
    public void setMinOnline(Integer minonline) {
        this.minonline = minonline;
    }
    public void setSave_logs(Boolean save_logs) {
        this.save_logs = save_logs;
    }
    public void setMotd(String[] motd) {
        this.motd = motd;
    }
    public void setMaintenanceMotd(String[] maintenancemotd) {
        this.maintenancemotd = maintenancemotd;
    }
    public void setAction(String[] action) {
        this.action = action;
    }
    public void setStartPort(Integer startport) {
        this.startport = startport;
    }
    public void setWorker(String worker) {
        this.worker = worker;
    }
    public void setMaintenanceAction(String[] maintenanceaction) {
        this.maintenanceaction = maintenanceaction;
    }
    public void setPlayerSize(String playersize) {
        this.playersize = playersize;
    }
    public void setCreatedAt(String createdat) {
        this.createdat = createdat;
    }
    public void setMaintenancePlayerSize(String maintenanceplayersize) {
        this.maintenanceplayersize = maintenanceplayersize;
    }
    /**
     * This methods are get methods.
     * @return
     */
    public Boolean getDynamic() {
        return dynamic;
    }
    public String getName() {
        return name;
    }
    public Boolean getMaintenance() {
        return maintenance;
    }
    public Boolean getSave_logs() {
        return save_logs;
    }
    public Integer getMaxOnline() {
        return maxonline;
    }
    public Integer getMemory() {
        return memory;
    }
    public Integer getStartPort() {
        return startport;
    }
    public String getCreatedAt() {
        return createdat;
    }
    public Integer getMinOnline() {
        return minonline;
    }
    public String getWorker() {
        return worker;
    }
    public String[] getAction() {
        return action;
    }
    public String getMaintenancePlayerSize() {
        return maintenanceplayersize;
    }
    public String getPlayerSize() {
        return playersize;
    }
    public String[] getMaintenanceAction() {
        return maintenanceaction;
    }
    public String[] getMotd() {
        return motd;
    }
    public String[] getMaintenanceMotd() {
        return maintenancemotd;
    }
}