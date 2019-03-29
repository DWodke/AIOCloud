package de.aiocloud.worker.objects;

public class ServerVersion {

    private String serverversion;
    private String minecraftversion;
    private String proxyversion;

    public ServerVersion(String serverversion, String minecraftversion, String proxyversion) {
        this.serverversion = serverversion;
        this.minecraftversion = minecraftversion;
        this.proxyversion = proxyversion;
    }
}
