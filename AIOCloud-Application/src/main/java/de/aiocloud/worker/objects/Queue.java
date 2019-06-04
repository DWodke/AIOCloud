package de.aiocloud.worker.objects;

import java.util.ArrayList;

public class Queue {

    private ArrayList<Proxy> proxyqueue;
    private Boolean canstartservice;

    /**
     * This is the initial method of the 'Queue' object.
     */
    public Queue() {
        this.proxyqueue = new ArrayList<Proxy>();
        this.canstartservice = true;
    }
    /**
     * Add a proxy to queue.
     * @param proxy
     */
    public void addToProxyQueue(Proxy proxy) {
        if(this.proxyqueue.contains(proxy)) {
            this.proxyqueue.add(proxy);
        }
    }
    /**
     * The following methods are GET and SET methods.
     */
    public Boolean getCanStartService() {
        return canstartservice;
    }
    public void setCanStartService(Boolean canstartservice) {
        this.canstartservice = canstartservice;
    }
}
