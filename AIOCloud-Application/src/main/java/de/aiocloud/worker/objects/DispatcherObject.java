package de.aiocloud.worker.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.aiocloud.AIOCloud;
import de.aiocloud.library.connection.Packet;
import de.aiocloud.library.security.SecurityManager;
import de.aiocloud.library.time.TimeHandler;
import de.aiocloud.logger.LoggingType;
import de.aiocloud.worker.Worker;
import de.aiocloud.worker.utils.FileManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class DispatcherObject {

    private Socket socket;
    private String host;
    private Integer port;
    private Integer tries;
    private Gson gson;
    private JsonParser parser;
    private ServerVersion serverversion;

    /**
     * This is the initial method of the 'DispatcherObject'.
     * @param host from dispatcher.
     * @param port from dispatcher.
     */
    public DispatcherObject(String host, Integer port) {
        this.host = host;
        this.port = port;
        this.tries = 0;
        this.gson = new GsonBuilder().create();
        this.parser = new JsonParser();
        this.connect();
    }
    /**
     * This method connect the worker with dispatcher.
     */
    public void connect() {
        if(this.tries == 10) {
            System.exit(1);
            return;
        }
        try {
            this.socket = new Socket(this.host, this.port);
            this.tries = this.tries +1;
            AIOCloud.logginghandler.print("Connection to dispatcher is successfully open!", LoggingType.INFO);
            this.tries = 0;
            this.readChannel();
            String hashedpw = SecurityManager.generateHash(FileManager.config.getWorkerId());
            JsonObject json = new JsonObject();
            json.addProperty("key", "registration");
            json.addProperty("authToken", hashedpw);
            json.addProperty("name", FileManager.config.getWorkerName());
            json.addProperty("totalRam", FileManager.config.getTotalRam());
            new Packet(json).send(this.socket);
            AIOCloud.logginghandler.print("Trying to verify with hash: " + hashedpw, LoggingType.INFO);
        } catch (Exception e) {
            e.printStackTrace();
            AIOCloud.logginghandler.print("Connection to the dispatcher does not work", LoggingType.INFO);
            AIOCloud.logginghandler.print("Dispatcher: " + this.host + "/" + this.port, LoggingType.INFO);
            try {
                Thread.sleep(1000);
            } catch (Exception e1) {
            }
        }
    }
    /**
     * This method start the reading of the packets from the dispatcher.
     */
    public void readChannel() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
                    String packet;
                    JsonObject json = null;
                    while((packet = reader.readLine()) != null) {
                        json = parser.parse(packet).getAsJsonObject();
                        String key = json.get("key").getAsString();
                        if(key != null) {
                            if(key.equalsIgnoreCase("registration")) {
                                serverversion = new ServerVersion(json.get("serverVersion").getAsString(), json.get("minecraftVersion").getAsString(), json.get("proxyVersion").getAsString());
                            } else if(key.equalsIgnoreCase("startProxy")) {
                                new Proxy(json.get("name").getAsString()
                                        , json.get("id").getAsInt()
                                        , json.get("group").getAsString()
                                        , json.get("port").getAsInt()
                                        , json.get("memory").getAsInt()
                                        , json.get("dispatcherHost").getAsString()
                                        , json.get("dispatcherPort").getAsInt()
                                        , json.get("dynamic").getAsBoolean()
                                        , json.get("save_logs").getAsBoolean()).start();
                            } else if(key.equalsIgnoreCase("triggerStop")) {
                                String servicetype = json.get("serviceType").getAsString();
                                if(servicetype.equalsIgnoreCase("proxy")) {
                                    String group = json.get("serviceGroup").getAsString();
                                    Integer id = json.get("serviceId").getAsInt();
                                    Worker.proxymanager.getProxy(id, group).delete();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    AIOCloud.logginghandler.print("Connection to dispatcher lost...", LoggingType.WARNING);
                    AIOCloud.logginghandler.print("Wait 10 seconds before stopping the worker!", LoggingType.INFO);
                    e.printStackTrace();
                    TimeHandler.sleep(1000L);
                    connect();
                }
            }
        }).start();
    }
    /**
     * The following methods are GET methods.
     */
    public Socket getSocket() {
        return socket;
    }
    public Integer getPort() {
        return port;
    }
    public String getHost() {
        return host;
    }
}