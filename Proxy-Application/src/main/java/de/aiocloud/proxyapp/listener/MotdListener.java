package de.aiocloud.proxyapp.listener;

import de.aiocloud.proxyapp.Main;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MotdListener implements Listener {

    /**
     * This event call's if an player ping the network.
     * @param e = event arguments.
     */
    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing ping = e.getResponse();
        ServerPing.Players p = ping.getPlayers();
        p.setMax(Main.dispatcher.getInfos().getMaxPlayer());
        p.setOnline(0);
        if(Main.dispatcher.getInfos().getMaintenance()) {
            ping.setVersion(new ServerPing.Protocol(Main.dispatcher.getInfos().getMaintenancePlayersize().replaceAll("%onlinePlayer%", 0 + "").replaceAll("%maxPlayer%", Main.dispatcher.getInfos().getMaxPlayer() + ""), -1));
            p.setSample(this.getMotdPlayerInfo(Arrays.asList(Main.dispatcher.getInfos().getMaintenanceAction())));
            ping.setDescription(Main.dispatcher.getInfos().getMaintenanceMotd()[0] + "\n" + Main.dispatcher.getInfos().getMaintenanceMotd()[1]);
        } else {
            ping.setVersion(new ServerPing.Protocol(Main.dispatcher.getInfos().getPlayerSize().replaceAll("%onlinePlayer%", 0 + "").replaceAll("%maxPlayer%", Main.dispatcher.getInfos().getMaxPlayer() + ""), -1));
            p.setSample(this.getMotdPlayerInfo(Arrays.asList(Main.dispatcher.getInfos().getAction())));
            ping.setDescription(Main.dispatcher.getInfos().getMotd()[0] + "\n" + Main.dispatcher.getInfos().getMotd()[1]);
        }
        ping.setPlayers(p);
        e.setResponse(ping);
    }
    /**
     * This method generates a ServerPing.PlayerInfo.
     */
    private ServerPing.PlayerInfo[] getMotdPlayerInfo(List<String> vars) {
        ServerPing.PlayerInfo[] playerInfos = new ServerPing.PlayerInfo[vars.size()];
        for (int i = 0; i < vars.size(); i++) {
            playerInfos[i] = new ServerPing.PlayerInfo(vars.get(i), UUID.randomUUID());
        }
        return playerInfos;
    }
}
