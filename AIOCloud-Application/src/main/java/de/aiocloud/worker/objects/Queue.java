package de.aiocloud.worker.objects;

import java.util.ArrayList;

public class Queue {

    private ArrayList<CloudProcess> queue;
    private Boolean canstartservice;
    private String currentstartedservice;

    /**
     * This is the initial method of the 'Queue' object.
     */
    public Queue() {
        this.queue = new ArrayList<CloudProcess>();
        this.canstartservice = true;
    }
    /**
     * Add a proxy to queue.
     * @param CloudProcess
     */
    public void addToQueue(CloudProcess process) {
        if(!this.queue.contains(process)) {
            this.queue.add(process);
        }
    }
    /**
     * The following methods are GET and SET methods.
     */
    public Boolean getCanStartService() {
        return canstartservice;
    }
    public String getCurrentStartedService() {
        return currentstartedservice;
    }
    public void setCanStartService(Boolean canstartservice) {
        this.canstartservice = canstartservice;
    }
    public void setCurrentStartedService(String currentstartedservice) {
        this.currentstartedservice = currentstartedservice;
    }
    public ArrayList<CloudProcess> getQueue() {
        return queue;
    }
}
