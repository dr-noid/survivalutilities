package com.ebkir.survivalutilities.listeners.custom;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.events.SpawnerSilkedEvent;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnerSilkedListener implements Listener {

    private final SurvivalUtilities plugin;

    public SpawnerSilkedListener(SurvivalUtilities plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSpawnerSilked(SpawnerSilkedEvent e) {
        Block spawner = e.getSpawner();

        CreatureSpawner creatureSpawner = (CreatureSpawner) spawner.getState();
        EntityType mob = creatureSpawner.getSpawnedType();

        Location loc = spawner.getLocation();

        ItemStack spawnerToDrop = getSpawnerFromEntityType(mob);

        loc.getWorld().dropItemNaturally(loc, spawnerToDrop);

        plugin.getServer().broadcast(Component.text("spawner should now drop..."));
    }

    private ItemStack getSpawnerFromEntityType(EntityType entityType) {
        ItemStack item = new ItemStack(Material.SPAWNER);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(startingUpperCase(entityType.name()) + " spawner"));

        NBTItem nbti = new NBTItem(item);
        nbti.setString("su_mob_type", entityType.name());

        return nbti.getItem();
    }

    private String startingUpperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
