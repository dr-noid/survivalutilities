package com.ebkir.survivalutilities.listeners;

import com.ebkir.survivalutilities.SurvivalUtilities;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerHitListener implements Listener {

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        if (SurvivalUtilities.isPvp()) {
            return;
        }

        boolean isPlayer = e.getEntity().getType().equals(EntityType.PLAYER);
        boolean damageCausedByContact = e.getCause().equals(EntityDamageEvent.DamageCause.CONTACT);

        if (isPlayer && damageCausedByContact) {
            e.setCancelled(true);
        }
    }
}
