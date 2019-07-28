package de.aiocloud.dispatcher.objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.aiocloud.AIOCloud;
import de.aiocloud.dispatcher.utils.FileManager;
import de.aiocloud.library.connection.Packet;
import de.aiocloud.library.states.ProxyState;
import de.aiocloud.library.states.ServerState;
import de.aiocloud.library.time.TimeHandler;
import de.aiocloud.logger.LoggingType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerObject {

    private String name;
    private Integer id;
    private Integer serverport;
    private ServerGroupObject servergroup;
    private ServerState state;
    private JsonParser parser;

    /**
     * This is the initial method of the 'ProxyObject'.
     */
    public ServerObject(String name, Integer serverport, ServerGroupObject servergroup) {
        this.name = name;
        this.state = ServerState.STARTING;
        this.servergroup = servergroup;
        this.serverport = serverport;
        try {
            String[] idsplitter = this.name.split(FileManager.config.getDefaultSeperator());
            this.id = Integer.parseInt(idsplitter[idsplitter.length -1]);
        } catch (NumberFormatException e) {
            AIOCloud.logginghandler.print("ERROR while parsing integer: " + e.getMessage(), LoggingType.ERROR);
        }
        this.parser = new JsonParser();
    }
    /**
     * This method delete this proxy-object.
     */
    public void delete() {
        this.servergroup.deleteServer(this.id);
    }
    /**
     * This methods are GET methods.
     */
    public Integer getPort() {
        return serverport;
    }
    public String getName() {
        return name;
    }
    public Integer getId() {
        return id;
    }
    /**
     * This methods are SET methods.
     */
    public void setState(ServerState state) {
        this.state = state;
    }
}
