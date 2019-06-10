package de.aiocloud.proxyapp;

import com.google.gson.JsonObject;
import de.aiocloud.proxyapp.library.connection.Packet;
import de.aiocloud.proxyapp.logger.LoggingHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Main extends Plugin {

    public static Main instance;
    public static LoggingHandler logginghandler;

    @Override
    public void onEnable() {
        this.init();
    }
    @Override
    public void onDisable() {
        JsonObject json = new JsonObject();
        json.addProperty("key", );
        new Packet()
    }
    /**
     * Init the app.
     */
    void init() {
        instance = this;
        logginghandler = new LoggingHandler();
        this.register();
    }
    /**
     * Register all listener and commands.
     */
    void register() {
        PluginManager manager = ProxyServer.getInstance().getPluginManager();
    }
}
