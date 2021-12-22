package com.ebkir.survivalutilities.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SpawnerPlacedEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player placer;
    private Block spawner;

    public SpawnerPlacedEvent(Player breaker, Block spawner) {
        this.placer = breaker;
        this.spawner = spawner;
    }

    public Player getBreaker() {
        return placer;
    }

    public Block getSpawner() {
        return spawner;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
