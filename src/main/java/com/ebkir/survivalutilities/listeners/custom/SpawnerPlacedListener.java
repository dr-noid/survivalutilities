package com.ebkir.survivalutilities.listeners.custom;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.events.custom.SpawnerPlacedEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SpawnerPlacedListener implements Listener {

    private final SurvivalUtilities plugin;
    private final NamespacedKey namespacedKey;

    public SpawnerPlacedListener(SurvivalUtilities plugin, NamespacedKey namespacedKey) {
        this.plugin = plugin;
        this.namespacedKey = namespacedKey;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSpawnerPlaced(SpawnerPlacedEvent e) {
        ItemStack item = e.getItemStack();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        if (container.getKeys().isEmpty()) {
            // Illegal item
            plugin.getLogger().info("Player: " + e.getPlayer().getName() + " placed an illegal spawner at " +
                    e.getSpawner().getLocation());
            return;
        }

        String entityName = container.get(namespacedKey, PersistentDataType.STRING);
        EntityType entityType = EntityType.valueOf(entityName);

        // Set data of placed block
        Block block = e.getSpawner();
        CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();
        creatureSpawner.setSpawnedType(entityType);
        creatureSpawner.update();
    }
}
