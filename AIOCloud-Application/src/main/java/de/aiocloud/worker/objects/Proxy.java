package de.aiocloud.worker.objects;

import de.aiocloud.AIOCloud;
import de.aiocloud.logger.LoggingType;
import de.aiocloud.worker.Worker;
import de.aiocloud.worker.utils.FileManager;

import java.io.File;
import java.io.IOException;

public class Proxy {

    private String name;
    private Integer id;
    private Integer port;
    private Integer memory;
    private String dispatcherhost;
    private Integer dispatcherport;
    private Process process;
    private Boolean startup;
    private Boolean dynamic;
    private Boolean save_logs;
    private File dir;

    /**
     * This is the initial method of the 'Proxy' object.
     * @param name of the proxy.
     * @param id of the proxy.
     * @param port of the proxy.
     * @param memory of the proxy
     * @param dispatcherhost of the dispatcher.
     * @param dispatcherport of the dispatcher.
     */
    public Proxy(String name, Integer id, Integer port, Integer memory, String dispatcherhost, Integer dispatcherport, Boolean dynamic, Boolean save_logs) {
        Worker.proxymanager.addProxy(this);
        this.name = name;
        this.id = id;
        this.port = port;
        this.memory = memory;
        this.dispatcherhost = dispatcherhost;
        this.dispatcherport = dispatcherport;
        this.dynamic = dynamic;
        this.startup = false;
        this.save_logs = save_logs;
        this.dir = new File(FileManager.tempdir.getPath() + "/" + this.name);
    }
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
    private void doSetup() {
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
            if(jarfile.exists()) {
                jarfile.delete();
            }
            FileManager.copyFile(jarfile, dir);
        } catch (IOException e) {
            AIOCloud.logginghandler.print("ERROR while copy file: " + FileManager.filesdir.getPath() + "/proxy.jar", LoggingType.ERROR);
        }
    }
    /**
     * The following methods are GET methods.
     * @return
     */
    public String getName() {
        return name;
    }
    public Integer getId() {
        return id;
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