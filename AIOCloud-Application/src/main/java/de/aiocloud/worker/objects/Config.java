package de.aiocloud.worker.objects;

import com.google.gson.JsonObject;

public class Config {

    private JsonObject json;

    private String workername;
    private String workeraddress;
    private String dispatcheraddress;
    private Integer dispatcherport;
    private String workerid;
    private Integer totalram;

    /**
     * This method is the initial method of the 'Config' object.
     * @param json gives the json from FileManager.
     */
    public Config(JsonObject json) {
        this.json = json;
        workername = json.get("name").getAsString();
        workeraddress = json.get("hostAddress").getAsString();
        dispatcheraddress = json.get("dispatcherAddress").getAsString();
        dispatcherport = json.get("dispatcherPort").getAsInt();
        workerid = json.get("authToken").getAsString();
        totalram = json.get("totalRam").getAsInt();
    }
    /**
     * The follow methods are GET methods.
     * @return every variable
     */
    public Integer getDispatcherPort() {
        return dispatcherport;
    }
    public Integer getTotalRam() {
        return totalram;
    }
    public String getDispatcherAddress() {
        return dispatcheraddress;
    }
    public String getWorkerAddress() {
        return workeraddress;
    }
    public String getWorkerId() {
        return workerid;
    }
    public String getWorkerName() {
        return workername;
    }
}