package de.aiocloud.dispatcher.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import de.aiocloud.AIOCloud;
import de.aiocloud.library.json.JsonArrayParser;
import de.aiocloud.logger.LoggingType;

import java.io.*;

public class ServerConfig {

    private String name;
    private String worker;
    private Integer startport;
    private Boolean save_logs;
    private Integer memory;
    private Integer maxplayer;
    private Integer minonline;
    private Integer maxonline;
    private Boolean dynamic;
    private Boolean maintenance;
    private String[] motd;
    private Double percentfornewserver;
    private String createdat;
    private File file;
    private static Gson gson;

    /**
     * This is the initial method of the 'ProxyConfig' object.
     * @param file is the file to write in.
     */
    public ServerConfig(File file) {
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
            json.addProperty("maxPlayer", this.maxplayer);
            json.addProperty("dynamic", this.dynamic);
            json.addProperty("maintenance", this.maintenance);
            json.addProperty("percentForNewServer", this.percentfornewserver);
            json.addProperty("createdAt", this.createdat);
            JsonArray motdarray = new JsonArray();
            for(String line : this.motd) {
                motdarray.add(line);
            }
            json.add("motd", motdarray);
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
            this.dynamic = json.get("dynamic").getAsBoolean();
            this.maintenance = json.get("maintenance").getAsBoolean();
            this.maxplayer = json.get("maxPlayer").getAsInt();
            this.motd = new JsonArrayParser(json.get("motd").getAsJsonArray()).getStringArray();
            this.memory = json.get("memory").getAsInt();
            this.minonline = json.get("minOnline").getAsInt();
            this.maxonline = json.get("maxOnline").getAsInt();
            this.createdat = json.get("createdAt").getAsString();
            this.save_logs = json.get("save_logs").getAsBoolean();
            this.worker = json.get("worker").getAsString();
            this.startport = json.get("startPort").getAsInt();
            this.percentfornewserver = json.get("percentForNewServer").getAsDouble();
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
    public void setStartPort(Integer startport) {
        this.startport = startport;
    }
    public void setWorker(String worker) {
        this.worker = worker;
    }
    public void setPercentForNewServer(Double percentfornewserver) {
        this.percentfornewserver = percentfornewserver;
    }
    public void setCreatedAt(String createdat) {
        this.createdat = createdat;
    }
    public void setMaxPlayer(Integer maxplayer) {
        this.maxplayer = maxplayer;
    }
    /**
     * This methods are get methods.
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
    public Double getPercentForNewServer() {
        return percentfornewserver;
    }
    public String[] getMotd() {
        return motd;
    }
    public Integer getMaxPlayer() {
        return maxplayer;
    }

}
