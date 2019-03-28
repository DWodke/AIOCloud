package de.aiocloud.dispatcher.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.aiocloud.AIOCloud;
import de.aiocloud.logger.LoggingType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class WorkerObject {

    private String hostaddress;
    private Integer port;
    private String workername;
    private String workerid;
    private ServerSocket serversocket;
    private Socket chatsocket;
    private Gson gson;
    private JsonParser parser;

    /**
     * This is the initial method of the 'WorkerObject' object.
     * @param host is the host-address of the worker.
     * @param port is the port of the worker.
     * @param name is the name of the worker.
     * @param id is the id of the worker.
     */
    public WorkerObject(String host, Integer port, String name, String id) {
        this.hostaddress = host;
        this.port = port;
        this.workername = name;
        this.workerid = id;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.parser = new JsonParser();
        this.open();
    }
    /**
     * This method open a channel for a worker.
     */
    public void open() {
        try {
            this.serversocket = new ServerSocket(this.port);
            connect();
        } catch (Exception e) {
            AIOCloud.logginghandler.print("Channel for worker with id '" + this.workerid + "' and the port '" + this.port + "' can't open...", LoggingType.ERROR);
        }
    }
    /**
     * This method connect to the worker.
     */
    public void connect() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Socket socket = null;
                    while((socket = serversocket.accept()) != null) {
                        chatsocket = socket;
                        AIOCloud.logginghandler.print("The worker with id '" + workerid + "' has connected successfully.", LoggingType.INFO);
                        readChannel();
                        break;
                    }
                } catch (Exception e) {
                    AIOCloud.logginghandler.print("Channel with port '" + port + "' caused an error.", LoggingType.ERROR);
                    AIOCloud.logginghandler.print("Trying again to open channel with port '" + port + "'.", LoggingType.INFO);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ignore) {
                    }
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

                                }
                            }
                        } else {
                            if(reader.read() == -1) {
                                AIOCloud.logginghandler.print("The worker with the id '" + workerid + "' has left the channel.", LoggingType.WARNING);
                                AIOCloud.logginghandler.print("Trying to connect to worker with id '" + workerid + "'.", LoggingType.INFO);
                                connect();
                                return;
                            }
                        }
                    }
                } catch (IOException e) {
                    AIOCloud.logginghandler.print("The worker with the id '" + workerid + "' has left the channel.", LoggingType.WARNING);
                    AIOCloud.logginghandler.print("Trying to connect to worker with id '" + workerid + "'.", LoggingType.INFO);
                    connect();
                    return;
                }
            }
        }).start();
    }
}