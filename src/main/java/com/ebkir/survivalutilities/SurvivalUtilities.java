package com.ebkir.survivalutilities;

import com.ebkir.survivalutilities.commands.home.DelHomeCommand;
import com.ebkir.survivalutilities.commands.home.HomeCommand;
import com.ebkir.survivalutilities.commands.home.HomesCommand;
import com.ebkir.survivalutilities.commands.home.SetHomeCommand;
import com.ebkir.survivalutilities.commands.spawn.SetSpawnCommand;
import com.ebkir.survivalutilities.commands.spawn.SpawnCommand;
import com.ebkir.survivalutilities.commands.teleport.TeleportCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SurvivalUtilities extends JavaPlugin {

    @Override
    public void onEnable() {
        SurvivalUtilities instance = this;
        final String spawnRoot = "spawn";
        final String homeRoot = "homes.";
        final String warpRoot = "warps.";

        getLogger().info("Starting SU");

        saveDefaultConfig();
        getCommand("spawn").setExecutor(new SpawnCommand(instance, spawnRoot));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(instance, spawnRoot));

        getCommand("home").setExecutor(new HomeCommand(instance, homeRoot));
        getCommand("sethome").setExecutor(new SetHomeCommand(instance, homeRoot));
        getCommand("delhome").setExecutor(new DelHomeCommand(instance, homeRoot));
        getCommand("homes").setExecutor(new HomesCommand(instance, homeRoot));

        getCommand("teleport").setExecutor(new TeleportCommand(instance));
    }

    @Override
    public void onDisable() {
        getLogger().info("Stopping SU");
        // Plugin shutdown logic
    }
}
