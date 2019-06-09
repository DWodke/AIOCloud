package de.aiocloud.proxyapp.logger;

import net.md_5.bungee.api.ProxyServer;

public class LoggingHandler {

    /**
     * This method print an log into console.
     * @param message of the log.
     * @param type of the log.
     */
    public void print(String message, LoggingType type) {
        if(type == LoggingType.ERROR) {
            ProxyServer.getInstance().getLogger().severe(message);
        } else if(type == LoggingType.WARNING) {
            ProxyServer.getInstance().getLogger().warning(message);
        } else {
            ProxyServer.getInstance().getLogger().info(message);
        }
    }
}
