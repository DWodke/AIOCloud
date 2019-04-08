package de.aiocloud.worker.objects;

import de.aiocloud.AIOCloud;
import de.aiocloud.logger.LoggingType;
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
    public Proxy(String name, Integer id, Integer port, Integer memory, String dispatcherhost, Integer dispatcherport, Boolean dynamic) {
        this.name = name;
        this.id = id;
        this.port = port;
        this.memory = memory;
        this.dispatcherhost = dispatcherhost;
        this.dispatcherport = dispatcherport;
        this.dynamic = dynamic;
        this.startup = false;
        this.dir = new File(FileManager.tempdir.getPath() + "/" + this.name);
    }
    public void start() {
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
            FileManager.copyFile(new File(FileManager.filesdir.getPath() + "/proxy.jar"), dir);
        } catch (IOException e) {
            AIOCloud.logginghandler.print("ERROR while copy file: " + FileManager.filesdir.getPath() + "/proxy.jar", LoggingType.ERROR);
        }
    }
}