package com.ebkir.survivalutilities.listeners;

import com.ebkir.survivalutilities.SurvivalUtilities;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SpawnerPlaceListener implements Listener {

    private final SurvivalUtilities plugin;

    public SpawnerPlaceListener(SurvivalUtilities plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        Block blockPlaced = e.getBlockPlaced();
        Player player = e.getPlayer();

        if (!e.getBlockPlaced().getType().equals(Material.SPAWNER)) {
            return;
        }
        plugin.getLogger().info("A spawner has been placed!");
        plugin.getLogger().info(e.getBlockReplacedState().toString());
        plugin.getLogger().info(e.getItemInHand().toString());
        plugin.getLogger().info(blockPlaced.getBlockData().getAsString());

//        NBTItem nbti = new NBTItem(item);

        handlePlacement(blockPlaced, EntityType.ZOMBIE);
    }

    public void handlePlacement(Block block, EntityType entityType) {
        CreatureSpawner spawner = (CreatureSpawner) block.getState();
        spawner.setSpawnedType(entityType);
        spawner.update();
    }
}
