package com.ebkir.survivalutilities;

import com.ebkir.survivalutilities.commands.home.DelHomeCommand;
import com.ebkir.survivalutilities.commands.home.HomeCommand;
import com.ebkir.survivalutilities.commands.home.SetHomeCommand;
import com.ebkir.survivalutilities.commands.spawn.SetSpawnCommand;
import com.ebkir.survivalutilities.commands.spawn.SpawnCommand;
import com.ebkir.survivalutilities.commands.teleport.TeleportCommand;
import com.google.common.reflect.ClassPath;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class SurvivalUtilities extends JavaPlugin {

    private final String spawnRoot = "spawn";
    private final String homeRoot = "homes.";
    private final String warpRoot = "warps.";

    @Override
    public void onEnable() {
        SurvivalUtilities instance = this;
        saveDefaultConfig();

        var commandList = new ArrayList<Command>();

        commandList.add(new HomeCommand(instance, homeRoot));
        commandList.add(new SetHomeCommand(instance, homeRoot));
        commandList.add(new DelHomeCommand(instance, homeRoot));

        commandList.add(new SpawnCommand(instance, spawnRoot));
        commandList.add(new SetSpawnCommand(instance, spawnRoot));

        commandList.add(new TeleportCommand(instance));

        addToCommandMap(this.getName(), commandList);

        var classesSet = findAllClassesUsingReflection(
                "com.ebkir.survivalutilities.commands.home");

        getLogger().info("Total classes in home: " + classesSet.size());
    }

    @Override
    public void onDisable() {
        getLogger().info("Stopping SU");
    }

    private void addToCommandMap(String fallbackPrefix, List<Command> commandList) {
        Bukkit.getCommandMap().registerAll(fallbackPrefix, commandList);
    }
    public static Set<Class> findAllClassesUsingReflection(String packageName) {
        try {
            return ClassPath.from(ClassLoader.getSystemClassLoader())
                    .getAllClasses()
                    .stream()
                    .filter(clazz -> clazz.getPackageName()
                            .equalsIgnoreCase(packageName))
                    .map(ClassPath.ClassInfo::load)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
