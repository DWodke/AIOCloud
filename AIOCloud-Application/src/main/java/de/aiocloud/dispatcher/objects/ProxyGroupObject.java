package de.aiocloud.dispatcher.objects;

import com.google.gson.JsonObject;
import de.aiocloud.AIOCloud;
import de.aiocloud.dispatcher.Dispatcher;
import de.aiocloud.dispatcher.configs.ProxyConfig;
import de.aiocloud.dispatcher.utils.FileManager;
import de.aiocloud.library.connection.Packet;
import de.aiocloud.logger.LoggingType;

import java.io.File;
import java.util.HashMap;

public class ProxyGroupObject {

    private HashMap<String, ProxyObject> availableproxies;
    private ProxyConfig config;
    private String name;
    private WorkerObject worker;

    /**
     * This is the initial method of the 'ProxyGroupObject'.
     * @param name of the proxygroup.
     */
    public ProxyGroupObject(String name, File file) {
        this.availableproxies = new HashMap<String, ProxyObject>();
        this.config = new ProxyConfig(file);
        this.config.load();
        this.name = name;
        this.worker = Dispatcher.availableworker.get(this.config.getWorker());
    }
    /**
     * GET a available proxy.
     * @param name of the proxy.
     * @return a ProxyObject.
     */
    public ProxyObject getAvailableProxy(String name) {
        return this.availableproxies.get(name);
    }
    public void startProxy(Integer id) {
        if(this.worker.hasWorkerEnoughMemory(this.config.getMemory())) {
            if(availableproxies.containsKey(this.name + FileManager.config.getDefaultSeperator() + id)) {
                AIOCloud.logginghandler.print("The proxy with id '" + id + "' can't start, because an proxy with the same id is started!", LoggingType.WARNING);
                return;
            }
            Integer connectionport = Dispatcher.portmanager.getNextFreeProxyConnectionPort();
            Dispatcher.portmanager.addProxyConnectionPort(connectionport);
            Integer port = Dispatcher.portmanager.getNextFreeProxyPort(this.config.getStartPort());
            Dispatcher.portmanager.addProxyPort(port);
            JsonObject json = new JsonObject();
            json.addProperty("key", "startProxy");
            json.addProperty("name", this.name + FileManager.config.getDefaultSeperator() + id);
            json.addProperty("id", id);
            json.addProperty("dispatcherHost", FileManager.config.getHostname());
            json.addProperty("dispatcherPort", connectionport);
            json.addProperty("port", port);
            json.addProperty("memory", this.config.getMemory());
            json.addProperty("dynamic", this.config.getDynamic());
            json.addProperty("save_logs", this.config.getSave_logs());
            new Packet(json).send(this.worker.getChatSocket());
            availableproxies.put(this.name + FileManager.config.getDefaultSeperator() + id, new ProxyObject(this.name + FileManager.config.getDefaultSeperator() + id, FileManager.config.getHostname(), connectionport, port, this));
        } else {
            AIOCloud.logginghandler.print("The proxy with id '" + id + "' can't start, because the worker doesn't have enough of memory!", LoggingType.WARNING);
        }
    }
    /**
     * This method do the setup of the group.
     */
    public void doStartup() {
        int i = 1;
        while(i <= this.config.getMinOnline()) {
            this.startProxy(i);
            i++;
        }
    }
    /**
     * The follow methods are GET methods.
     */
    public String getName() {
        return name;
    }
    public ProxyConfig getConfig() {
        return config;
    }
    public WorkerObject getWorker() {
        return worker;
    }
    public HashMap<String, ProxyObject> getAvailableProxies() {
        return availableproxies;
    }
}