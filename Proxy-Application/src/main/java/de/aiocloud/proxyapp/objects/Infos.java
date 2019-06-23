package de.aiocloud.proxyapp.objects;

import com.google.gson.JsonObject;
import de.aiocloud.proxyapp.library.json.JsonArrayParser;

public class Infos {

    private String[] motd;
    private String[] maintenancemotd;
    private String playersize;
    private String maintenanceplayersize;
    private String[] action;
    private String[] maintenanceaction;
    private String worker;
    private Boolean maintenance;
    private Integer maxplayer;
    private String[] header;
    private String[] footer;
    private String currentgroup;
    private String name;

    /**
     * This is the initial method of the 'Infos' object.
     * @param json from dispatcher.
     */
    public Infos(JsonObject json) {
        this.motd = new JsonArrayParser(json.get("motd").getAsJsonArray()).getStringArray();
        this.maintenancemotd = new JsonArrayParser(json.get("maintenanceMotd").getAsJsonArray()).getStringArray();
        this.playersize = json.get("playerSize").getAsString();
        this.maintenanceplayersize = json.get("maintenancePlayerSize").getAsString();
        this.action = new JsonArrayParser(json.get("action").getAsJsonArray()).getStringArray();
        this.maintenanceaction = new JsonArrayParser(json.get("maintenanceAction").getAsJsonArray()).getStringArray();
        this.worker = json.get("worker").getAsString();
        this.maintenance = json.get("maintenance").getAsBoolean();
        this.maxplayer = json.get("maxPlayer").getAsInt();
        this.header = new JsonArrayParser(json.get("header").getAsJsonArray()).getStringArray();
        this.footer = new JsonArrayParser(json.get("footer").getAsJsonArray()).getStringArray();
        this.currentgroup = json.get("currentGroup").getAsString();
        this.name = json.get("name").getAsString();
    }
    /**
     * The following methods are GET methods.
     */
    public String[] getHeader() {
        return header;
    }
    public String[] getFooter() {
        return footer;
    }
    public String[] getAction() {
        return action;
    }
    public Boolean getMaintenance() {
        return maintenance;
    }
    public Integer getMaxPlayer() {
        return maxplayer;
    }
    public String getMaintenancePlayersize() {
        return maintenanceplayersize;
    }
    public String getPlayerSize() {
        return playersize;
    }
    public String getCurrentGroup() {
        return currentgroup;
    }
    public String getWorker() {
        return worker;
    }
    public String[] getMaintenanceAction() {
        return maintenanceaction;
    }
    public String[] getMaintenanceMotd() {
        return maintenancemotd;
    }
    public String[] getMotd() {
        return motd;
    }
    public String getName() {
        return name;
    }
}
