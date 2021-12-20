package com.ebkir.survivalutilities.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SpawnerBreakEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player breaker;
    private Block spawner;

    public SpawnerBreakEvent(Player breaker, Block spawner) {
        this.breaker = breaker;
        this.spawner = spawner;
    }

    public Player getBreaker() {
        return breaker;
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
