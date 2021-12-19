package com.ebkir.survivalutilities;

import com.ebkir.survivalutilities.commands.home.*;
import com.ebkir.survivalutilities.commands.home.SetWarpCommand;
import com.ebkir.survivalutilities.commands.spawn.SetSpawnCommand;
import com.ebkir.survivalutilities.commands.spawn.SpawnCommand;
import com.ebkir.survivalutilities.commands.teleport.TeleportCommand;
import com.ebkir.survivalutilities.models.Home;
import com.ebkir.survivalutilities.utils.ClassCounter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class SurvivalUtilities extends JavaPlugin {

    private final String spawnRoot = "spawn";
    private final String homeRoot = "homes.";
    private final String warpRoot = "warps";

    @Override
    public void onEnable() {
        SurvivalUtilities instance = this;

        ConfigurationSerialization.registerClass(Home.class);

        saveDefaultConfig();

        var commandList = new ArrayList<Command>();

        commandList.add(new HomeCommand(instance, homeRoot));
        commandList.add(new DelHomeCommand(instance, homeRoot));
        commandList.add(new HomesCommand(instance, homeRoot));
        commandList.add(new SetHomeCommand(instance, homeRoot));

        commandList.add(new SpawnCommand(instance, spawnRoot));
        commandList.add(new SetSpawnCommand(instance, spawnRoot));

        commandList.add(new SetWarpCommand(instance, warpRoot));

        commandList.add(new TeleportCommand(instance));

        addToCommandMap(this.getName(), commandList);
    }

    @Override
    public void onDisable() {
        saveConfig();
        getLogger().info("Stopping SU");
    }

    private void addToCommandMap(String fallbackPrefix, List<Command> commandList) {
        Bukkit.getCommandMap().registerAll(fallbackPrefix, commandList);
    }

}
