package de.aiocloud.proxyapp;

import com.google.gson.JsonObject;
import de.aiocloud.proxyapp.library.connection.Packet;
import de.aiocloud.proxyapp.listener.MotdListener;
import de.aiocloud.proxyapp.logger.LoggingHandler;
import de.aiocloud.proxyapp.objects.ConnectionFile;
import de.aiocloud.proxyapp.objects.Dispatcher;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.io.File;

public class Main extends Plugin {

    public static Main instance;
    public static LoggingHandler logginghandler;
    public static Dispatcher dispatcher;
    public static ConnectionFile connectionfile;

    @Override
    public void onEnable() {
        this.init();
    }
    @Override
    public void onDisable() {
        JsonObject json = new JsonObject();
        json.addProperty("key", "serviceStopped");
        json.addProperty("processName", dispatcher.getInfos().getName());
        new Packet(json).send(dispatcher.getSocket());
    }
    /**
     * Init the app.
     */
    void init() {
        instance = this;
        logginghandler = new LoggingHandler();
        if(!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        connectionfile = new ConnectionFile(new File(getDataFolder().getPath() + "/connectionFile.json"));
        connectionfile.load();
        dispatcher = new Dispatcher(connectionfile.getHost(), connectionfile.getPort());
        dispatcher.connect();
        this.register();
    }
    /**
     * Register all listener and commands.
     */
    void register() {
        PluginManager manager = ProxyServer.getInstance().getPluginManager();
        manager.registerListener(instance, new MotdListener());
    }
}
