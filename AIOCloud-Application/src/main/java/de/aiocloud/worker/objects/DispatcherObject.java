package de.aiocloud.worker.objects;

import java.net.InetSocketAddress;
import java.net.Socket;

public class DispatcherObject {

    private Socket socket;
    private String host;
    private Integer port;

    /**
     * This is the initial method of the 'DispatcherObject'.
     * @param host from dispatcher.
     * @param port from dispatcher.
     */
    public DispatcherObject(String host, Integer port) {
        this.host = host;
        this.port = port;
    }
    /**
     * This method connect the worker with dispatcher.
     */
    public void connect() {
        try {
            this.socket = new Socket(new InetSocketAddress(this.host, this.port));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
