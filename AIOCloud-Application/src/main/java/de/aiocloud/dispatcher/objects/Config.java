package de.aiocloud.dispatcher.objects;

import com.google.gson.JsonObject;

public class Config {

    private JsonObject json;

    private String hostname;
    private Integer webport;
    private String defaultseperator;
    private String minecraftversion;
    private String serverversion;
    private String proxyversion;

    /**
     * This method is the initial method of the 'Config' object.
     * @param json gives the json from FileManager.
     */
    public Config(JsonObject json) {
        this.json = json;
        hostname = json.get("hostAddress").getAsString();
        defaultseperator = json.get("defaultSeparator").getAsString();
        webport = json.get("webPort").getAsInt();
        minecraftversion = json.get("minecraftVersion").getAsString();
        serverversion = json.get("serverVersion").getAsString();
        proxyversion = json.get("proxyVersion").getAsString();
    }
    /**
     * The follow methods are GET methods.
     * @return every variable
     */
    public Integer getWebPort() {
        return webport;
    }
    public String getDefaultSeperator() {
        return defaultseperator;
    }
    public String getHostname() {
        return hostname;
    }
    public String getMinecraftVersion() {
        return minecraftversion;
    }
    public String getProxyVersion() {
        return proxyversion;
    }
    public String getServerVersion() {
        return serverversion;
    }
}