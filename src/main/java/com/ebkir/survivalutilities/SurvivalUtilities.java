package com.ebkir.survivalutilities;

import com.ebkir.survivalutilities.commands.homev2.HomeCommand;
import com.ebkir.survivalutilities.commands.homev2.SetHomeCommand;
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

    @Override
    public void onEnable() {
        SurvivalUtilities instance = this;
        saveDefaultConfig();

        var commandList = new ArrayList<Command>();

        commandList.add(new HomeCommand(instance, homeRoot));
        commandList.add(new SetHomeCommand(instance, homeRoot));

        addToCommandMap(this.getName(), commandList);

        var classesSet = findAllClassesUsingReflection(
                "com.ebkir.survivalutilities.commands.homev2");

        getLogger().info("Total classes in homev2: " + classesSet.size());

//        getCommand("specialhome").setExecutor(new HomeObjectCommand(instance));

//        getCommand("spawn").setExecutor(new SpawnCommand(instance, spawnRoot));
//        getCommand("setspawn").setExecutor(new SetSpawnCommand(instance, spawnRoot));
//
//        getCommand("home").setExecutor(new HomeCommand(instance, homeRoot));
//        getCommand("sethome").setExecutor(new SetHomeCommand(instance, homeRoot));
//        getCommand("delhome").setExecutor(new DelHomeCommand(instance, homeRoot));
//        getCommand("homes").setExecutor(new HomesCommand(instance, homeRoot));
//
//        getCommand("teleport").setExecutor(new TeleportCommand(instance));
    }

    @Override
    public void onDisable() {
        getLogger().info("Stopping SU");
        // Plugin shutdown logic
    }

    private void addToCommandMap(String fallbackPrefix, List<Command> commandList) {
        Bukkit.getCommandMap().registerAll(fallbackPrefix, commandList);
    }
}
