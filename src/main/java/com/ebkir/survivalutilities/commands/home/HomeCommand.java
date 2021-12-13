package com.ebkir.survivalutilities.commands.home;

import com.ebkir.survivalutilities.SurvivalUtilities;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class HomeCommand implements CommandExecutor {

    private final SurvivalUtilities plugin;
    private final String rootPath;

    public HomeCommand(SurvivalUtilities plugin, String rootPath) {
        this.plugin = plugin;
        this.rootPath = rootPath;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Wrong arguments");
            return false;
        }

        String homeName = args[0];
        String playerConfigRoot = rootPath + player.getUniqueId();
        var homesMapList = plugin.getConfig().getMapList(playerConfigRoot);

        if (homesMapList.isEmpty()) {
            player.sendMessage("You don't have any homes yet");
            return true;
        }

        var optionalHome =
                homesMapList.stream()
                        .filter((map) -> map.containsKey(homeName))
                        .toList();

        if (optionalHome.isEmpty()) {
            player.sendMessage("You don't have a home with this name.");
            return true;
        }

        Location homeLocation = (Location) optionalHome.get(0).get(homeName);
        player.teleport(homeLocation);
        return true;
    }
}
