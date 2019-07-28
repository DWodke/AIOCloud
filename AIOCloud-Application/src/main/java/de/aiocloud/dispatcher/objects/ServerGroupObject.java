package de.aiocloud.dispatcher.objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.aiocloud.AIOCloud;
import de.aiocloud.dispatcher.Dispatcher;
import de.aiocloud.dispatcher.configs.ServerConfig;
import de.aiocloud.dispatcher.utils.FileManager;
import de.aiocloud.library.connection.Packet;
import de.aiocloud.library.states.ServerState;
import de.aiocloud.logger.LoggingType;

import java.io.File;
import java.util.HashMap;

public class ServerGroupObject {

    private HashMap<String, ServerObject> availableserver;
    private ServerConfig config;
    private String name;
    private WorkerObject worker;

    /**
     * This is the initial method of the 'ServerGroupObject'.
     * @param name of the servergroup.
     */
    public ServerGroupObject(String name, File file) {
        this.availableserver = new HashMap<String, ServerObject>();
        this.config = new ServerConfig(file);
        this.config.load();
        this.name = name;
        this.worker = Dispatcher.availableworker.get(this.config.getWorker());
    }
    /**
     * GET a available server.
     * @param name of the server.
     * @return a ServerObject.
     */
    public ServerObject getAvailableServer(String name) {
        return this.availableserver.get(name);
    }
    /**
     * This method starts a proxy service.
     */
    public void startServer(Integer id) {
        if(this.worker.hasWorkerEnoughMemory(this.config.getMemory())) {
            if(availableserver.containsKey(this.name + FileManager.config.getDefaultSeperator() + id)) {
                return;
            }
            Integer port = Dispatcher.portmanager.getNextFreeServerPort(this.config.getStartPort());
            Dispatcher.portmanager.addServerPort(port);
            JsonObject json = new JsonObject();
            json.addProperty("key", "startServer");
            json.addProperty("name", this.name + FileManager.config.getDefaultSeperator() + id);
            json.addProperty("id", id);
            json.addProperty("group", this.name);
            json.addProperty("dispatcherHost", FileManager.config.getHostname());
            json.addProperty("dispatcherPort", "25564");
            json.addProperty("port", port);
            json.addProperty("memory", this.config.getMemory());
            json.addProperty("dynamic", this.config.getDynamic());
            json.addProperty("save_logs", this.config.getSave_logs());
            json.addProperty("maxPlayer", this.config.getMaxPlayer());
            json.addProperty("maintenance", this.config.getMaintenance());
            json.addProperty("percentForNewServer", this.config.getPercentForNewServer());
            JsonArray motdarray = new JsonArray();
            for(String line : this.config.getMotd()) {
                motdarray.add(line);
            }
            json.add("motd", motdarray);
            new Packet(json).send(this.worker.getChatSocket());
            availableserver.put(this.name + FileManager.config.getDefaultSeperator() + id, new ServerObject(this.name + FileManager.config.getDefaultSeperator() + id, port, this));
        } else {
            AIOCloud.logginghandler.print("The proxy with id '" + id + "' can't start, because the worker doesn't have enough of memory!", LoggingType.WARNING);
        }
    }
    /**
     * This method deletes a server from the dispatcher.
     * @param id from the server.
     */
    public void deleteServer(Integer id) {
        if(this.availableserver.containsKey(this.name + FileManager.config.getDefaultSeperator() + id)) {
            this.availableserver.remove(this.name + FileManager.config.getDefaultSeperator() + id);
        }
    }
    /**
     * This method trigger a stop to the worker.
     * @param id of the server.
     */
    public void triggerStop(Integer id) {
        JsonObject json = new JsonObject();
        json.addProperty("key", "triggerStop");
        json.addProperty("serviceType", "server");
        json.addProperty("serviceGroup", this.name);
        json.addProperty("serviceId", id);
        this.getAvailableServer(this.name + FileManager.config.getDefaultSeperator() + id).setState(ServerState.STOPPING);
        new Packet(json).send(this.worker.getChatSocket());
    }
    /**
     * This method do the setup of the group.
     */
    public void startAllServicesUnderMinOnline() {
        int i = 1;
        while(i <= this.config.getMinOnline()) {
            this.startServer(i);
            i++;
        }
    }
    /**
     * The follow methods are GET methods.
     */
    public String getName() {
        return name;
    }
    public ServerConfig getConfig() {
        return config;
    }
    public WorkerObject getWorker() {
        return worker;
    }
    public HashMap<String, ServerObject> getAvailableServer() {
        return availableserver;
    }
}
