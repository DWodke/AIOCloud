package de.aiocloud.dispatcher.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PortManager {

    private ArrayList<Integer> workerports;
    private ArrayList<Integer> proxyports;

    /**
     * This is the initial method of the 'PortManager' object.
     */
    public PortManager() {
        this.workerports = new ArrayList<Integer>();
        this.proxyports = new ArrayList<Integer>();
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
     * This method gives you an free proxy port.
     * @return integer.
     */
    public Integer getNextFreeProxyPort() {
        Integer port = 13000;
        while(true) {
            if(!this.workerports.contains(port)) {
                return port;
            }
            port = port++;
        }
    }
}
