package de.aiocloud.dispatcher.objects;

import de.aiocloud.dispatcher.utils.FileManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PortManager {

    private ArrayList<Integer> workerports;
    private ArrayList<Integer> proxyports;
    private ArrayList<Integer> serverports;
    private ArrayList<Integer> proxyconnectionports;

    /**
     * This is the initial method of the 'PortManager' object.
     */
    public PortManager() {
        this.workerports = new ArrayList<Integer>();
        this.proxyports = new ArrayList<Integer>();
        this.serverports = new ArrayList<Integer>();
        this.proxyconnectionports = new ArrayList<Integer>();
    }
    /**
     * This method add a port to the list.
     * @param port is an worker port.
     */
    public void addWorkerPort(Integer port) {
        this.workerports.add(port);
    }
    /**
     * This method add a port to the list.
     * @param port is an proxy port.
     */
    public void addProxyPort(Integer port) {
        this.proxyports.add(port);
    }
    /**
     * This method add a port to the list.
     * @param port is an server port.
     */
    public void addServerPort(Integer port) {
        this.serverports.add(port);
    }
    /**
     * This method add a port to the list.
     * @param port is an proxy port.
     */
    public void addProxyConnectionPort(Integer port) {
        this.proxyconnectionports.add(port);
    }
    /**
     * This method gives you an free worker port.
     * @return integer.
     */
    public Integer getNextFreeWorkerPort() {
        Integer port = 12000;
        while(true) {
            if(!this.workerports.contains(port)) {
                return port;
            }
            port = port++;
        }
    }
    /**
     * This method gives you an free proxy-connection port.
     * @return integer.
     */
    public Integer getNextFreeProxyConnectionPort() {
        Integer port = FileManager.config.getProxyConnectionStartPort();
        while(true) {
            if(!this.proxyconnectionports.contains(port)) {
                return port;
            }
            port = port++;
        }
    }
    /**
     * This method gives you an free proxy port.
     * @return integer.
     */
    public Integer getNextFreeProxyPort(Integer startport) {
        Integer port = startport;
        while(true) {
            if(!this.proxyports.contains(port)) {
                return port;
            }
            port = port++;
        }
    }
    /**
     * This method gives you an free server port.
     * @return integer.
     */
    public Integer getNextFreeServerPort(Integer startport) {
        Integer port = startport;
        while(true) {
            if(!this.serverports.contains(port)) {
                return port;
            }
            port = port++;
        }
    }
}