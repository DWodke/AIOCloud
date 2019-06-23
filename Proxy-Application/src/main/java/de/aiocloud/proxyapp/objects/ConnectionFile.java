package de.aiocloud.proxyapp.objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ConnectionFile {

    private Gson gson;
    private JsonObject json;
    private JsonParser parser;
    private File file;
    private String host;
    private Integer port;

    /**
     * This is the initial method of the 'ConnectionFile' object.
     * @param file from the ConnectionFile.
     */
    public ConnectionFile(File file) {
        this.gson = new Gson();
        this.json = new JsonObject();
        this.parser = new JsonParser();
        this.file = file;
    }
    /**
     * This method load all data from the json-file.
     */
    public void load() {
        try {
            JsonReader reader = new JsonReader(new FileReader(this.file));
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            this.host = json.get("host").getAsString();
            this.port = json.get("port").getAsInt();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * The following methods are GET methods.
     */
    public String getHost() {
        return host;
    }
    public Integer getPort() {
        return port;
    }
}
