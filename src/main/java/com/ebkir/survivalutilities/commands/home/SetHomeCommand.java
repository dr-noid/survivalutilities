package com.ebkir.survivalutilities.commands.home;

import com.ebkir.survivalutilities.SurvivalUtilities;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetHomeCommand implements CommandExecutor {

    private final SurvivalUtilities plugin;
    private final String rootPath;

    public SetHomeCommand(SurvivalUtilities plugin, String rootPath) {
        this.plugin = plugin;
        this.rootPath = rootPath;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length == 0) {
            return false;
        }

        if (args.length > 1) {
            sender.sendMessage("Too many arguments!");
            return false;
        }

        String homeName = args[0];
        String playerConfigRoot = rootPath + player.getUniqueId();
        Location playerLocation = player.getLocation();

        var homesMapList = plugin.getConfig().getMapList(playerConfigRoot);
        plugin.getLogger().info(homesMapList.toString());

        if (homesMapList.isEmpty()) {
            List<HashMap<String, Location>> homesList = new ArrayList<>();

            var home = new HashMap<String, Location>();
            home.put(homeName, playerLocation);
            homesList.add(home);

            plugin.getConfig().set(playerConfigRoot, homesList);
            plugin.saveConfig();

            player.sendMessage("Home " + homeName + " is saved.");
            return true;
        }

        boolean homeSameName = homesMapList.stream().anyMatch(map -> map.containsKey(homeName));

        if (homeSameName) {
            plugin.getLogger().info("Sethome with same homename is called: " + homeName);
            player.sendMessage("You can't sethome with the same name.");
            return true;
        }

        // not the same name and already has homes

        var newHome = new HashMap<String, Location>();
        newHome.put(homeName, playerLocation);

        homesMapList.add(newHome);
        plugin.getConfig().set(playerConfigRoot, homesMapList);
        plugin.saveConfig();
        player.sendMessage("Home " + homeName + " is saved.");
        return true;
    }
}
