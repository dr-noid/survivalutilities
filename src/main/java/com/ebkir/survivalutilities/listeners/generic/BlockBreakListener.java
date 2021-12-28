package com.ebkir.survivalutilities.listeners.generic;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.events.custom.SpawnerSilkedEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BlockBreakListener implements Listener {

    private final SurvivalUtilities plugin;

    public BlockBreakListener(SurvivalUtilities plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();

        if (!isSpawner(block)) return;

        Player player = e.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();

        if (!usingPickaxe(tool)) return;

        if (!hasSilkTouch(tool)) return;

        Bukkit.getServer().getPluginManager().callEvent(new SpawnerSilkedEvent(e.getPlayer(), block));
    }

    private boolean usingPickaxe(ItemStack tool){
        List<Material> acceptedMaterials = List.of(Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE);
        return acceptedMaterials.contains(tool.getType());
    }

    private boolean hasSilkTouch(ItemStack tool) {
        return tool.containsEnchantment(Enchantment.SILK_TOUCH);
    }

    private boolean isSpawner(Block block) {
        return block.getType().equals(Material.SPAWNER);
    }
}
