package com.ebkir.survivalutilities.listeners;

import com.ebkir.survivalutilities.events.SpawnerBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block blockBroken = e.getBlock();
        boolean brokenBlockIsSpawner = blockBroken.getType().equals(Material.SPAWNER);
        boolean hasSilkTouch = e.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH);

        if (brokenBlockIsSpawner && hasSilkTouch) {
            Bukkit.getServer().getPluginManager().callEvent(new SpawnerBreakEvent(e.getPlayer(), blockBroken));
        }
    }
}
