package com.ebkir.survivalutilities.listeners;

import com.ebkir.survivalutilities.SurvivalUtilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerHitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        if (SurvivalUtilities.isPvp()) {
            return;
        }

        Bukkit.getLogger().info("EntityDamageByEntityEvent occured, DamageCause: " + e.getCause());

        boolean damagerIsPlayer = e.getEntityType().equals(EntityType.PLAYER);
        boolean player = e.getDamager().getType().equals(EntityType.PLAYER);

        Bukkit.getLogger().info("Entity involed: " + e.getEntityType().toString());
        Bukkit.getLogger().info("Damager type: " + e.getDamager().getType().toString());

        boolean isPlayer = e.getEntity().getType().equals(EntityType.PLAYER);
        boolean damageCausedByContact = e.getCause().equals(EntityDamageEvent.DamageCause.CONTACT);

        if (isPlayer && damageCausedByContact) {
            e.setDamage(0);
            e.setCancelled(true);
        }
    }
}
