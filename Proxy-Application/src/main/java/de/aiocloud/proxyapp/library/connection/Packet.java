package de.aiocloud.proxyapp.library.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.aiocloud.proxyapp.Main;
import de.aiocloud.proxyapp.logger.LoggingType;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Packet {

    private JsonObject json;
    private Gson gson;
    private JsonParser parser;
    private Socket socket;

    /**
     * This is the initial method of the 'Packet' object.
     * @param json
     */
    public Packet(JsonObject json) {
        this.json = json;
        this.gson = new GsonBuilder().create();
        this.parser = new JsonParser();
    }
    /**
     * This method send the json to the socket output.
     * @param socket
     */
    public void send(Socket socket) {
        if(this.socket == null) {
            this.socket = socket;
        }
        if(socket.isConnected() && socket != null) {
            try {
                OutputStream out = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"), true);
                writer.write(gson.toJson(this.json) + "\n");
                writer.flush();
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Main.logginghandler.print("ERROR while send packet...", LoggingType.ERROR);
        }
    }
}
