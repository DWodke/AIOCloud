package de.aiocloud.dispatcher.objects;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.aiocloud.AIOCloud;
import de.aiocloud.dispatcher.Dispatcher;
import de.aiocloud.dispatcher.configs.WorkerConfig;
import de.aiocloud.dispatcher.utils.FileManager;
import de.aiocloud.library.connection.Packet;
import de.aiocloud.library.security.SecurityManager;
import de.aiocloud.library.time.TimeHandler;
import de.aiocloud.logger.LoggingType;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class WorkerObject {

    private String workername;
    private ServerSocket serversocket;
    private Socket chatsocket;
    private JsonParser parser;
    private Boolean connection;
    private WorkerConfig config;
    private Integer totalram;
    private WorkerObject instance;

    /**
     * This is the initial method of the 'WorkerObject' object.
     * @param file is the config file of the worker.
     * @param name is the name of the worker.
     */
    public WorkerObject(String name, File file) {
        this.config = new WorkerConfig(file);
        this.config.load();
        this.workername = name;
        this.parser = new JsonParser();
        this.connection = false;
        this.instance = this;
        this.totalram = 0;
        this.open();
    }
    /**
     * This method open a channel for a worker.
     */
    public void open() {
        this.connection = false;
        try {
            this.serversocket = new ServerSocket(this.config.getPort());
            connect();
        } catch (Exception e) {
            AIOCloud.logginghandler.print("Channel for worker with id '" + this.config.getId() + "' and the port '" + this.config.getPort() + "' can't open...", LoggingType.ERROR);
        }
    }
    /**
     * This method connect to the worker.
     */
    public void connect() {
        this.connection = false;
        new Thread(new Runnable() {
            public void run() {
                try {
                    Socket socket = null;
                    while((socket = serversocket.accept()) != null) {
                        chatsocket = socket;
                        readChannel();
                        break;
                    }
                } catch (Exception e) {
                    AIOCloud.logginghandler.print("Channel with port '" + config.getPort() + "' caused an error.", LoggingType.ERROR);
                    AIOCloud.logginghandler.print("Trying again to open channel with port '" + config.getPort() + "'.", LoggingType.INFO);
                    TimeHandler.sleep(3000L);
                    connect();
                    return;
                }
            }
        }).start();
    }
    /**
     * This method read all packets from worker.
     */
    public void readChannel() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(chatsocket.getInputStream()));
                    String packet = null;
                    JsonObject json = null;
                    while(true) {
                        if((packet = reader.readLine()) != null) {
                            json = parser.parse(packet).getAsJsonObject();
                            String key = json.get("key").getAsString();
                            if(key != null) {
                                if(key.equalsIgnoreCase("registration")) {
                                    if(SecurityManager.checkHash(config.getAuthToken(), json.get("authToken").getAsString())) {
                                        totalram = json.get("totalRam").getAsInt();
                                        AIOCloud.logginghandler.print("The worker '" + workername + "' has verified successfully.", LoggingType.INFO);
                                        AIOCloud.logginghandler.print("He has " + totalram + " megabyte of memory available.", LoggingType.INFO);
                                        JsonObject jsonObject = new JsonObject();
                                        jsonObject.addProperty("key", "registration");
                                        jsonObject.addProperty("minecraftVersion", FileManager.config.getMinecraftVersion());
                                        jsonObject.addProperty("serverVersion", FileManager.config.getServerVersion());
                                        jsonObject.addProperty("proxyVersion", FileManager.config.getProxyVersion());
                                        new Packet(jsonObject).send(chatsocket);
                                        connection = true;
                                        for(ProxyGroupObject group : Dispatcher.proxygroups.values()) {
                                            if(group.getWorker() == instance) {
                                                group.doStartup();
                                            }
                                        }
                                    } else {
                                        AIOCloud.logginghandler.print("Connection to '" + workername + "' refused.", LoggingType.WARNING);
                                        AIOCloud.logginghandler.print("Reason: verify-key does not equals!", LoggingType.WARNING);
                                        chatsocket.close();
                                        connect();
                                        return;
                                    }
                                }
                            }
                        } else {
                            if(reader.read() == -1) {
                                AIOCloud.logginghandler.print("The worker '" + workername + "' has left the channel.", LoggingType.WARNING);
                                AIOCloud.logginghandler.print("Trying to connect to worker '" + workername + "'.", LoggingType.INFO);
                                connect();
                                return;
                            }
                        }
                    }
                } catch (IOException e) {
                    AIOCloud.logginghandler.print("The worker '" + workername + "' has left the channel.", LoggingType.WARNING);
                    AIOCloud.logginghandler.print("Trying to connect to worker '" + workername + "'.", LoggingType.INFO);
                    connect();
                } catch (NoSuchAlgorithmException e) {
                    AIOCloud.logginghandler.print("The worker '" + workername + "' has left the channel.", LoggingType.WARNING);
                    AIOCloud.logginghandler.print("Trying to connect to worker '" + workername + "'.", LoggingType.INFO);
                    connect();
                } catch (InvalidKeySpecException e) {
                    AIOCloud.logginghandler.print("The worker '" + workername + "' has left the channel.", LoggingType.WARNING);
                    AIOCloud.logginghandler.print("Trying to connect to worker '" + workername + "'.", LoggingType.INFO);
                    connect();
                }
            }
        }).start();
    }
    /**
     * This method shows you if the worker still has enough memory.
     * @param memory
     * @return
     */
    public Boolean hasWorkerEnoughMemory(Integer memory) {
        Integer used = 0;
        for(ProxyGroupObject group : Dispatcher.proxygroups.values()) {
            if(group.getWorker() == this) {
                for(ProxyObject proxy : group.getAvailableProxies().values()) {
                    used = used + group.getConfig().getMemory();
                }
            }
        }
        Integer max = this.totalram;
        if(used >= max) {
            return false;
        } else if(used < max) {
            if(used + memory > max) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    /**
     * GET the socket from the worker.
     * @return
     */
    public Socket getChatSocket() {
        return chatsocket;
    }
    /**
     * GET is the worker started.
     * @return
     */
    public Boolean isConnected() {
        return connection;
    }
}