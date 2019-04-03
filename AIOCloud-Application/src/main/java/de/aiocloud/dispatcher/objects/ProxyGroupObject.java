package de.aiocloud.dispatcher.objects;

import com.google.gson.JsonObject;
import de.aiocloud.AIOCloud;
import de.aiocloud.dispatcher.Dispatcher;
import de.aiocloud.dispatcher.utils.FileManager;
import de.aiocloud.library.connector.Packet;
import de.aiocloud.logger.LoggingType;

import java.util.HashMap;

public class ProxyGroupObject {

    private HashMap<String, ProxyObject> availableproxies;
    private String name;
    private Integer startport;
    private WorkerObject worker;
    private Boolean save_logs;
    private Integer memory;
    private Boolean dynamic;
    private Integer minonline;
    private Integer maxonline;
    private Boolean maintenance;
    private String[] motd;

    /**
     * This is the initial method of the 'ProxyGroupObject'.
     * @param name of the proxygroup.
     * @param startport is the startport of the group.
     * @param worker is the worked worker.
     * @param save_logs
     * @param memory
     * @param dynamic
     * @param minonline
     * @param maxonline
     */
    public ProxyGroupObject(String name, Integer startport, WorkerObject worker, Boolean save_logs, Integer memory, Boolean dynamic, Integer minonline, Integer maxonline, Boolean maintenance, String[] motd) {
        this.availableproxies = new HashMap<String, ProxyObject>();
        this.name = name;
        this.worker = worker;
        this.save_logs = save_logs;
        this.memory = memory;
        this.dynamic = dynamic;
        this.minonline = minonline;
        this.maxonline = maxonline;
        this.startport = startport;
        this.maintenance = maintenance;
        this.motd = motd;
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
        if(this.worker.hasWorkerEnoughMemory(this.memory)) {
            for(ProxyObject proxy : availableproxies.values()) {
                if(proxy.getId() == id) {
                    AIOCloud.logginghandler.print("The proxy with id '" + id + "' can't start, because an proxy with the same id is started!", LoggingType.WARNING);
                    return;
                }
            }
            Integer connectionport = Dispatcher.portmanager.getNextFreeProxyConnectionPort();
            Dispatcher.portmanager.addProxyConnectionPort(connectionport);
            Integer port = Dispatcher.portmanager.getNextFreeProxyPort(this.startport);
            Dispatcher.portmanager.addProxyPort(port);
            JsonObject json = new JsonObject();
            json.addProperty("key", "startProxy");
            json.addProperty("name", this.name + FileManager.config.getDefaultSeperator() + id);
            json.addProperty("id", id);
            json.addProperty("dispatcherHost", FileManager.config.getHostname());
            json.addProperty("dispatcherPort",  connectionport);
            json.addProperty("port", port);
            json.addProperty("memory", this.memory);
            new Packet(json).send(this.worker.getChatSocket());
            new ProxyObject(this.name + FileManager.config.getDefaultSeperator() + id, FileManager.config.getHostname(), connectionport, port, this);
        } else {
            AIOCloud.logginghandler.print("The proxy with id '" + id + "' can't start, because the worker doesn't have enough of memory!", LoggingType.WARNING);
        }
    }
    /**
     * The follow methods are GET methods.
     */
    public String getName() {
        return name;
    }
    public Boolean getDynamic() {
        return dynamic;
    }
    public Boolean isSave_logs() {
        return save_logs;
    }
    public HashMap<String, ProxyObject> getAvailableProxies() {
        return availableproxies;
    }
    public Integer getMaxOnline() {
        return maxonline;
    }
    public Integer getMinOnline() {
        return minonline;
    }
    public Integer getMemory() {
        return memory;
    }
    public Integer getStartPort() {
        return startport;
    }
    public WorkerObject getWorker() {
        return worker;
    }
}