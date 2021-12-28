package com.ebkir.survivalutilities.listeners.custom;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.events.custom.SpawnerSilkedEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.TileState;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SpawnerSilkedListener implements Listener {

    private final SurvivalUtilities plugin;
    private final NamespacedKey namespacedKey;

    public SpawnerSilkedListener(SurvivalUtilities plugin, NamespacedKey namespacedKey) {
        this.plugin = plugin;
        this.namespacedKey = namespacedKey;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSpawnerSilked(SpawnerSilkedEvent e) {
        Block spawner = e.getSpawner();

        CreatureSpawner creatureSpawner = (CreatureSpawner) spawner.getState();
        EntityType entityType = creatureSpawner.getSpawnedType();
        BlockState state = spawner.getState();
        Location loc = spawner.getLocation();

        if (state instanceof TileState) {
            TileState tileState = (TileState) spawner.getState();
            PersistentDataContainer container = tileState.getPersistentDataContainer();

            container.set(namespacedKey, PersistentDataType.STRING, entityType.name());
            tileState.update();
        }

        ItemStack spawnerToDrop = getSpawnerFromEntityType(entityType);
        loc.getWorld().dropItemNaturally(loc, spawnerToDrop);
    }

    private ItemStack getSpawnerFromEntityType(EntityType entityType) {
        ItemStack item = new ItemStack(Material.SPAWNER);
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, entityType.name());
        Component itemName = (Component.text().
                content(startingUpperCase(entityType.toString().toLowerCase()) + " Spawner").
                asComponent());
        meta.displayName(itemName);
        item.setItemMeta(meta);
        return item;
    }

    private String startingUpperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
