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

public class ProxyConfig {

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
    private String[] maintenancemotd;
    private String[] action;
    private String[] maintenanceaction;
    private String[] header;
    private String[] footer;
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
            json.addProperty("maxPlayer", this.maxplayer);
            json.addProperty("dynamic", this.dynamic);
            json.addProperty("maintenance", this.maintenance);
            json.addProperty("playerSize", this.playersize);
            json.addProperty("maintenancePlayerSize", this.maintenanceplayersize);
            json.addProperty("createdAt", this.createdat);
            JsonArray motdarray = new JsonArray();
            JsonArray maintenancemotdarray = new JsonArray();
            JsonArray action = new JsonArray();
            JsonArray maintenanceaction = new JsonArray();
            JsonArray headerarray = new JsonArray();
            JsonArray footerarray = new JsonArray();
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
            for(String line : this.header) {
                headerarray.add(line);
            }
            for(String line : this.footer) {
                footerarray.add(line);
            }
            json.add("motd", motdarray);
            json.add("action", action);
            json.add("maintenanceMotd", maintenancemotdarray);
            json.add("maintenanceAction", maintenanceaction);
            json.add("tabHeader", headerarray);
            json.add("tabFooter", footerarray);
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
            this.action = new JsonArrayParser(json.get("action").getAsJsonArray()).getStringArray();
            this.maintenanceaction = new JsonArrayParser(json.get("maintenanceAction").getAsJsonArray()).getStringArray();
            this.motd = new JsonArrayParser(json.get("motd").getAsJsonArray()).getStringArray();
            this.maintenancemotd = new JsonArrayParser(json.get("maintenanceMotd").getAsJsonArray()).getStringArray();
            this.memory = json.get("memory").getAsInt();
            this.minonline = json.get("minOnline").getAsInt();
            this.maxonline = json.get("maxOnline").getAsInt();
            this.maintenanceplayersize = json.get("maintenancePlayerSize").getAsString();
            this.playersize = json.get("playerSize").getAsString();
            this.createdat = json.get("createdAt").getAsString();
            this.save_logs = json.get("save_logs").getAsBoolean();
            this.worker = json.get("worker").getAsString();
            this.startport = json.get("startPort").getAsInt();
            this.header = new JsonArrayParser(json.get("tabHeader").getAsJsonArray()).getStringArray();
            this.footer = new JsonArrayParser(json.get("tabFooter").getAsJsonArray()).getStringArray();
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
    public void setFooter(String[] footer) {
        this.footer = footer;
    }
    public void setHeader(String[] header) {
        this.header = header;
    }
    public void setMaxPlayer(Integer maxplayer) {
        this.maxplayer = maxplayer;
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
    public String[] getFooter() {
        return footer;
    }
    public String[] getHeader() {
        return header;
    }
    public Integer getMaxPlayer() {
        return maxplayer;
    }
}