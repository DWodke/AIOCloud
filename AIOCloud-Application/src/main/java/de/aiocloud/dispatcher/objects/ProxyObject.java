package de.aiocloud.dispatcher.objects;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.aiocloud.AIOCloud;
import de.aiocloud.dispatcher.utils.FileManager;
import de.aiocloud.library.time.TimeHandler;
import de.aiocloud.logger.LoggingType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyObject {

    private String name;
    private Integer id;
    private String address;
    private Integer dispatcherport;
    private Integer serverport;
    private ProxyGroupObject proxygroup;
    private ServerSocket serversocket;
    private Socket chatsocket;
    private JsonParser parser;

    /**
     * This is the initial method of the 'ProxyObject'.
     */
    public ProxyObject(String name, String address, Integer dispatcherport, Integer serverport, ProxyGroupObject proxygroup) {
        this.name = name;
        this.address = address;
        this.dispatcherport = dispatcherport;
        this.proxygroup = proxygroup;
        this.serverport = serverport;
        String[] idsplitter = this.name.split(FileManager.config.getDefaultSeperator());
        this.id = Integer.parseInt(idsplitter[idsplitter.length -1]);
        this.parser = new JsonParser();
        this.open();
    }
    /**
     * This method open the channel for the proxy.
     */
    public void open() {
        try {
            this.serversocket = new ServerSocket(this.dispatcherport);
            AIOCloud.logginghandler.print("Open channel [host: " + this.address + ", port: " + this.dispatcherport + "]", LoggingType.ERROR);
            this.connect();
        } catch (Exception e) {
            AIOCloud.logginghandler.print("ERROR while open channel for proxy '" + this.name + "'.", LoggingType.ERROR);
            TimeHandler.sleep(1000L);
            this.open();
        }
    }
    /**
     * This method connect to the proxy.
     */
    public void connect() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Socket socket = null;
                    while((socket = serversocket.accept()) != null) {
                        chatsocket = socket;
                        AIOCloud.logginghandler.print("Service connected [name: " + name + ", host: " + address + ", port: " + dispatcherport + ", type: Proxy]", LoggingType.INFO);
                        readChannel();
                        break;
                    }
                } catch (Exception e) {
                    TimeHandler.sleep(3000L);
                    connect();
                }
            }
        }).start();
    }
    /**
     * This method read all packets from proxy.
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
                                AIOCloud.logginghandler.print("Service disconnected [name: " + name + ", host: " + address + ", port: " + dispatcherport + ", type: Proxy]", LoggingType.WARNING);
                                connect();
                                return;
                            }
                        }
                    }
                } catch (IOException e) {
                    AIOCloud.logginghandler.print("Service disconnected [name: " + name + ", host: " + address + ", port: " + dispatcherport + ", type: Proxy]", LoggingType.WARNING);
                    connect();
                }
            }
        }).start();
    }
    /**
     * This methods are GET methods.
     */
    public Integer getDispatcherPort() {
        return dispatcherport;
    }
    public Integer getPort() {
        return serverport;
    }
    public ProxyGroupObject getProxyGroup() {
        return proxygroup;
    }
    public String getAddress() {
        return address;
    }
    public String getName() {
        return name;
    }
    public Integer getId() {
        return id;
    }
}