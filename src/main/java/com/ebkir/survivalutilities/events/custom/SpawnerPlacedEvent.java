package com.ebkir.survivalutilities.events.custom;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SpawnerPlacedEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Block spawner;
    private final ItemStack itemStack;
    private final Player player;

    public SpawnerPlacedEvent(Block spawner, ItemStack itemStack, Player player) {
        this.spawner = spawner;
        this.itemStack = itemStack;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getSpawner() {
        return spawner;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
