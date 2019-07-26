package de.aiocloud.worker.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.aiocloud.AIOCloud;
import de.aiocloud.library.connection.Packet;
import de.aiocloud.library.time.TimeHandler;
import de.aiocloud.logger.LoggingType;
import de.aiocloud.worker.Worker;
import de.aiocloud.worker.utils.FileManager;

import java.io.*;
import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Proxy {

    private String name;
    private Integer id;
    private String group;
    private Integer port;
    private Integer memory;
    private String dispatcherhost;
    private Integer dispatcherport;
    private Process process;
    private Boolean startup;
    private Boolean dynamic;
    private Boolean save_logs;
    private File dir;
    private static Gson gson;

    /**
     * This is the initial method of the 'Proxy' object.
     * @param name of the proxy.
     * @param id of the proxy.
     * @param port of the proxy.
     * @param memory of the proxy
     * @param dispatcherhost of the dispatcher.
     * @param dispatcherport of the dispatcher.
     */
    public Proxy(String name, Integer id, String group, Integer port, Integer memory, String dispatcherhost, Integer dispatcherport, Boolean dynamic, Boolean save_logs) {
        Worker.proxymanager.addProxy(this);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.name = name;
        this.id = id;
        this.group = group;
        this.port = port;
        this.memory = memory;
        this.dispatcherhost = dispatcherhost;
        this.dispatcherport = dispatcherport;
        this.dynamic = dynamic;
        this.startup = false;
        this.save_logs = save_logs;
        this.dir = new File(FileManager.tempdir.getPath() + "/" + this.name);
    }
    /**
     * This method start the proxy-server.
     */
    public void start() {
        if(Worker.queue.getCanStartService()) {
            Worker.queue.setCanStartService(false);
            this.startup = true;
            this.doSetup();
            ProcessBuilder pb = new ProcessBuilder();
            pb.directory(dir);
            pb.command("java", "-jar", "-Xms" + this.memory + "M", "-Xmx" + this.memory + "M", "-jar", "proxy.jar");
            try {
                this.process = pb.start();
            } catch (IOException e) {
                AIOCloud.logginghandler.print("ERROR while start the proxy-process: " + this.dir.getPath(), LoggingType.ERROR);
                this.startup = false;
            }
        } else {
            Worker.queue.addToProxyQueue(this);
        }
    }
    /**
     * This method delete the proxy-server.
     */
    public void delete() {
        Worker.proxymanager.removeProxy(this.name);
            try {
                PrintStream writer = new PrintStream(this.process.getOutputStream());
                writer.println("end");
                writer.flush();
                final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.scheduleAtFixedRate(new Runnable() {
                    public void run() {
                        process.destroy();
                        process = null;
                        JsonObject json = new JsonObject();
                        json.addProperty("key", "serviceDeleted");
                        json.addProperty("processType", "proxy");
                        new Packet(json).send(Worker.dispatcher.getSocket());
                        scheduler.shutdown();
                    }
                }, 1, 1, TimeUnit.SECONDS);
            } catch (Exception e) {
                AIOCloud.logginghandler.print("ERROR while delete the proxy-process: " + this.dir.getPath(), LoggingType.ERROR);
            }
    }
    /**
     * This method do the startup for the new task.
     */
    public void doSetup() {
        if(this.dynamic) {
            if(this.dir.exists()) {
                try {
                    FileManager.deleteDir(this.dir);
                } catch (IOException e) {
                    AIOCloud.logginghandler.print("ERROR while deleting the directory: " + this.dir.getPath(), LoggingType.ERROR);
                }
            }
            this.dir.mkdir();
        } else {
            if(!this.dir.exists()) {
                this.dir.mkdir();
            }
        }
        try {
            File jarfile = new File(FileManager.filesdir.getPath() + "/proxy.jar");
            File dirjar = new File(dir.getPath() + "/proxy.jar");
            if(dirjar.exists()) {
                dirjar.delete();
            }
            FileManager.copyFile(jarfile, dirjar);
            File plugindirectory = new File(dir.getPath() + "/plugins");
            if(!plugindirectory.exists()) {
                plugindirectory.mkdir();
            }
            File proxyapi = new File(FileManager.filesdir.getPath() + "/proxy-api.jar");
            File currentproxyapi = new File(plugindirectory.getPath() + "/proxy-api.jar");
            if(currentproxyapi.exists()) {
                currentproxyapi.delete();
            }
            //URI uri = URI.create("jar:file:" + currentproxyapi.getAbsolutePath());
            FileManager.copyFile(proxyapi, currentproxyapi);
            File cloudplugindir = new File(dir.getPath() + "/plugins/Proxy-Application");
            if(!cloudplugindir.exists()) {
                cloudplugindir.mkdir();
            }
            File connectfile = new File(dir.getPath() + "/plugins/Proxy-Application/connectionFile.json");
            if(connectfile.exists()) {
                connectfile.delete();
            }
            connectfile.createNewFile();
            JsonObject json = new JsonObject();
            json.addProperty("host", this.dispatcherhost);
            json.addProperty("port", this.dispatcherport);
            try {
                FileWriter writer = new FileWriter(connectfile);
                writer.write(gson.toJson(json));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                AIOCloud.logginghandler.print("ERROR while create proxy-connection-file.", LoggingType.ERROR);
            }
        } catch (IOException e) {
            AIOCloud.logginghandler.print("ERROR while copy file: " + FileManager.filesdir.getPath() + "/proxy.jar", LoggingType.ERROR);
        }
    }
    /**
     * The following methods are GET methods.
     */
    public String getName() {
        return name;
    }
    public Integer getId() {
        return id;
    }
    public String getGroup() {
        return group;
    }
    public Integer getMemory() {
        return memory;
    }
    public Integer getPort() {
        return port;
    }
    public Boolean getDynamic() {
        return dynamic;
    }
    public Integer getDispatcherport() {
        return dispatcherport;
    }
    public Process getProcess() {
        return process;
    }
    public Boolean getSave_logs() {
        return save_logs;
    }
    public File getDir() {
        return dir;
    }
    public String getDispatcherhost() {
        return dispatcherhost;
    }
    public Boolean getStartup() {
        return startup;
    }
}