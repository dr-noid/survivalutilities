package com.ebkir.survivalutilities.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        boolean pluginCause = e.getCause().equals(PlayerTeleportEvent.TeleportCause.PLUGIN);
        boolean commandCause = e.getCause().equals(PlayerTeleportEvent.TeleportCause.COMMAND);

        if (pluginCause || commandCause) {
            e.getPlayer().setVelocity(new Vector(0, 0, 0));
            e.getPlayer().setFallDistance(0f);
            Bukkit.getLogger().info("player: " + e.getPlayer().getName() +
                    " had their falling damage negated, because of TeleportCause: " + e.getCause());
        }
    }
}
