package com.ebkir.survivalutilities.listeners;

import com.ebkir.survivalutilities.events.SpawnerBreakEvent;
import com.ebkir.survivalutilities.utils.Messager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomListeners implements Listener {

    @EventHandler
    public void onSpawnerBreak(SpawnerBreakEvent e) {
        CreatureSpawner cs = (CreatureSpawner) e.getSpawner().getState();
        EntityType mob = cs.getSpawnedType();
        String lowerCaseMob = mob.toString().toLowerCase();
        String mobName = lowerCaseMob.substring(0, 1).toUpperCase() + lowerCaseMob.substring(1);
        Player breaker = e.getBreaker();

        breaker.getInventory().addItem(makeSpawner(cs, mobName));
        Messager.send(breaker, "&3" + mobName + "&a spawner mined");
    }

    private ItemStack makeSpawner(CreatureSpawner spawner, String mobName) {
        ItemStack spawnerToGive = new ItemStack(spawner.getType());
        ItemMeta spawnerMeta = spawnerToGive.getItemMeta();

        spawnerMeta.displayName(Component.text(mobName + " spawner").asComponent());

        spawnerToGive.setItemMeta(spawnerMeta);

        return spawnerToGive;
    }
}
