package de.aiocloud.worker.objects;

import de.aiocloud.AIOCloud;
import de.aiocloud.library.connection.Download;
import de.aiocloud.logger.LoggingType;

import java.util.HashMap;

public class ServerVersion {

    private String serverversion;
    private String minecraftversion;
    private String proxyversion;

    private String proxyurl;
    private HashMap<String, String> spigot_jars;
    private HashMap<String, String> paper_jars;

    /**
     * This is the initial method of the 'ServerVersion' method.
     * @param serverversion jar-version (Paper, Spigot...)
     * @param minecraftversion 1.7, 1.8....
     * @param proxyversion jar-version (Waterfall, Bungeecord....)
     */
    public ServerVersion(String serverversion, String minecraftversion, String proxyversion) {
        this.serverversion = serverversion;
        this.minecraftversion = minecraftversion;
        this.proxyversion = proxyversion;
        this.spigot_jars = new HashMap<String, String>();
        this.paper_jars = new HashMap<String, String>();
        if(this.proxyversion.equalsIgnoreCase("BUNGEECORD")) {
            this.proxyurl = "https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar";
        } else if(this.proxyversion.equalsIgnoreCase("WATERFALL")) {
            this.proxyurl = "https://papermc.io/ci/job/Waterfall/lastSuccessfulBuild/artifact/Waterfall-Proxy/bootstrap/target/Waterfall.jar";
        }
        this.spigot_jars.put("1.7.10", "https://cdn.getbukkit.org/spigot/spigot-1.7.10-SNAPSHOT-b1657.jar");
        this.spigot_jars.put("1.8", "https://cdn.getbukkit.org/spigot/spigot-1.8-R0.1-SNAPSHOT-latest.jar");
        this.spigot_jars.put("1.8.3", "https://cdn.getbukkit.org/spigot/spigot-1.8.3-R0.1-SNAPSHOT-latest.jar");
        this.spigot_jars.put("1.8.4", "https://cdn.getbukkit.org/spigot/spigot-1.8.4-R0.1-SNAPSHOT-latest.jar");
        this.spigot_jars.put("1.8.5", "https://cdn.getbukkit.org/spigot/spigot-1.8.5-R0.1-SNAPSHOT-latest.jar");
        this.spigot_jars.put("1.8.6", "https://cdn.getbukkit.org/spigot/spigot-1.8.6-R0.1-SNAPSHOT-latest.jar");
        this.spigot_jars.put("1.8.7", "https://cdn.getbukkit.org/spigot/spigot-1.8.7-R0.1-SNAPSHOT-latest.jar");
        this.spigot_jars.put("1.8.8", "https://cdn.getbukkit.org/spigot/spigot-1.8.8-R0.1-SNAPSHOT-latest.jar");
        this.spigot_jars.put("1.9", "https://cdn.getbukkit.org/spigot/spigot-1.9-R0.1-SNAPSHOT-latest.jar");
        this.spigot_jars.put("1.9.2", "https://cdn.getbukkit.org/spigot/spigot-1.9.2-R0.1-SNAPSHOT-latest.jar");
        this.spigot_jars.put("1.9.4", "https://cdn.getbukkit.org/spigot/spigot-1.9.4-R0.1-SNAPSHOT-latest.jar");
        this.spigot_jars.put("1.10", "https://cdn.getbukkit.org/spigot/spigot-1.10-R0.1-SNAPSHOT-latest.jar");
        this.spigot_jars.put("1.10.2", "https://cdn.getbukkit.org/spigot/spigot-1.10.2-R0.1-SNAPSHOT-latest.jar");
        this.spigot_jars.put("1.11", "https://cdn.getbukkit.org/spigot/spigot-1.11.jar");
        this.spigot_jars.put("1.11.1", "https://cdn.getbukkit.org/spigot/spigot-1.11.1.jar");
        this.spigot_jars.put("1.11.2", "https://cdn.getbukkit.org/spigot/spigot-1.11.2.jar");
        this.spigot_jars.put("1.12", "https://cdn.getbukkit.org/spigot/spigot-1.12.jar");
        this.spigot_jars.put("1.12.1", "https://cdn.getbukkit.org/spigot/spigot-1.12.1.jar");
        this.spigot_jars.put("1.12.2", "https://cdn.getbukkit.org/spigot/spigot-1.12.2.jar");
        this.spigot_jars.put("1.13", "https://cdn.getbukkit.org/spigot/spigot-1.13.jar");
        this.spigot_jars.put("1.13.1", "https://cdn.getbukkit.org/spigot/spigot-1.13.1.jar");
        this.spigot_jars.put("1.13.2", "https://cdn.getbukkit.org/spigot/spigot-1.13.2.jar");

        this.paper_jars.put("1.7.10", "https://yivesmirror.com/files/paper/PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar");
        this.paper_jars.put("1.8.8", "https://yivesmirror.com/files/paper/PaperSpigot-1.8.8-R0.1-SNAPSHOT-latest.jar");
        this.paper_jars.put("1.11.2", "https://yivesmirror.com/files/paper/PaperSpigot-1.11.2-b1104.jar");
        this.paper_jars.put("1.12.2", "https://yivesmirror.com/files/paper/Paper-1.12.2-b1611.jar");
        this.paper_jars.put("1.13.2", "https://yivesmirror.com/files/paper/Paper-latest.jar");
        AIOCloud.logginghandler.print("Trying to download server.jar...", LoggingType.INFO);
        if(this.serverversion.equalsIgnoreCase("SPIGOT")) {
            new Download(this.spigot_jars.get(this.minecraftversion), "./worker/files/server.jar");
        } else if(this.serverversion.equalsIgnoreCase("PAPER")) {
            new Download(this.paper_jars.get(this.minecraftversion), "./worker/files/server.jar");
        }
        AIOCloud.logginghandler.print("Download complete!", LoggingType.INFO);
        AIOCloud.logginghandler.print("Trying to download proxy.jar...", LoggingType.INFO);
        new Download(this.proxyurl, "./worker/files/proxy.jar");
        AIOCloud.logginghandler.print("Download complete!", LoggingType.INFO);
    }
}
