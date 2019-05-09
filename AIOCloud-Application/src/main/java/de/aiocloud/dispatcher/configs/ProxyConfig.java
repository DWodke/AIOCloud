package de.aiocloud.dispatcher.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import de.aiocloud.AIOCloud;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ProxyConfig {

    private String name;
    private String worker;
    private Integer startport;
    private Boolean save_logs;
    private Integer memory;
    private Integer minonline;
    private Integer maxonline;
    private Boolean dynamic;
    private Boolean maintenance;
    private String[] motd;
    private String[] maintenancemotd;
    private String[] action;
    private String[] maintenanceaction;
    private String playersize;
    private String maintenanceplayersize;
    private String createdat;
    private File file;
    private static Gson gson;

    /**
     * This is the initial method of the 'ProxyConfig' object.
     * @param file is the file to write in.
     */
    public ProxyConfig(File file) {
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
            json.addProperty("worker", this.worker);
            json.addProperty("startPort", this.startport);
            json.addProperty("save_logs", this.save_logs);
            json.addProperty("memory", this.memory);
            json.addProperty("minOnline", this.minonline);
            json.addProperty("maxOnline", this.maxonline);
            json.addProperty("dynamic", this.dynamic);
            json.addProperty("maintenance", this.maintenance);
            json.addProperty("playerSize", this.playersize);
            json.addProperty("maintenancePlayerSize", this.maintenanceplayersize);
            json.addProperty("createdAt", this.createdat);
            JsonArray motdarray = new JsonArray();
            JsonArray maintenancemotdarray = new JsonArray();
            JsonArray action = new JsonArray();
            JsonArray maintenanceaction = new JsonArray();
            for(String line : this.motd) {
                motdarray.add(line);
            }
            for(String line : this.maintenancemotd) {
                maintenancemotdarray.add(line);
            }
            for(String line : this.action) {
                action.add(line);
            }
            for(String line : this.maintenanceaction) {
                maintenanceaction.add(line);
            }
            json.add("motd", motdarray);
            json.add("action", action);
            json.add("maintenanceMotd", maintenancemotdarray);
            json.add("maintenanceAction", maintenanceaction);
            /*action.add("&6AIOCloud - Application &7| &cThe simple, extensive and exclusive system.");
            action.add("&6Programmed by: &cDominik Wodke");
            action.add("&6Version: &c" + AIOCloud.versionhandler.getCurrentVersion());*/
            writer.write(gson.toJson(json));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method load all data from the config file.
     */
    public void load() {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            this.name = json.get("name").getAsString();
            this.dynamic = json.get("dynamic").getAsBoolean();
            this.maintenance = json.get("maintenance").getAsBoolean();
            JsonArray actionarray = json.get("action").getAsJsonArray();
            Arrays.
            this.action =
        } catch (Exception e) {
            e.printStackTrace();
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
}