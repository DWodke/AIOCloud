package de.aiocloud.proxyapp.objects;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.aiocloud.proxyapp.Main;
import de.aiocloud.proxyapp.library.time.TimeHandler;
import de.aiocloud.proxyapp.logger.LoggingType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Dispatcher {

    private String host;
    private Integer port;
    private Socket socket;
    private JsonParser parser;
    private Infos infos;

    /**
     * This methid is the initial method if the 'Dispatcher' object.
     * @param hostname of the dispatcher,
     * @param port of the dispatcher.
     */
    public Dispatcher(String hostname, Integer port) {
        this.host = hostname;
        this.port = port;
        this.parser = new JsonParser();
    }
    /**
     * This method connects this application to the dispatcher.
     */
    public void connect() {
        try {
            this.socket = new Socket(this.host, this.port);
            Main.logginghandler.print("Connection to dispatcher successfully open.", LoggingType.ERROR);
            this.readChannel();
        } catch (Exception e) {
            Main.logginghandler.print("Connection to the dispatcher does not work", LoggingType.ERROR);
            Main.logginghandler.print("Dispatcher: " + this.host + "/" + this.port, LoggingType.ERROR);
            TimeHandler.sleep(1000L);
        }
    }
    /**
     * This method reads the stream from the socket.
     */
    private void readChannel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
                    String packet;
                    JsonObject json = null;
                    while ((packet = reader.readLine()) != null) {
                        json = parser.parse(packet).getAsJsonObject();
                        String key = json.get("key").getAsString();
                        if(key != null) {
                            if(key.equalsIgnoreCase("proxyInformation")) {
                                infos = new Infos(json);
                            }
                        }
                    }
                } catch (Exception e) {
                    Main.logginghandler.print("Connection to dispatcher lost...", LoggingType.WARNING);
                    Main.logginghandler.print("Wait for a new connection...", LoggingType.INFO);
                    TimeHandler.sleep(1000L);
                    connect();
                }
            }
        }).start();
    }
    /**
     * The following methods are GET methods.
     */
    public Infos getInfos() {
        return infos;
    }
    public Socket getSocket() {
        return socket;
    }
}
