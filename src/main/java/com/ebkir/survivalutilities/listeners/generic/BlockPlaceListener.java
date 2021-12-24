package com.ebkir.survivalutilities.listeners.generic;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.events.SpawnerPlacedEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceListener implements Listener {

    private final SurvivalUtilities plugin;

    public BlockPlaceListener(SurvivalUtilities plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        ItemStack itemInHand = e.getItemInHand();
        Player player = e.getPlayer();

        if (!isSpawner(block)) return;

        Bukkit.getPluginManager().callEvent(new SpawnerPlacedEvent(block, itemInHand, player));
    }

    private boolean isSpawner(Block block) {
        return block.getType().equals(Material.SPAWNER);
    }

}
