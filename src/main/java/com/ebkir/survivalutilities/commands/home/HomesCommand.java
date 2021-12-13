package com.ebkir.survivalutilities.commands.home;

import com.ebkir.survivalutilities.SurvivalUtilities;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HomesCommand implements CommandExecutor {

    private final SurvivalUtilities plugin;
    private final String rootPath;

    public HomesCommand(SurvivalUtilities plugin, String rootPath) {
        this.plugin = plugin;
        this.rootPath = rootPath;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length != 0) {
            return false;
        }

        String configHomePath = rootPath + player.getUniqueId();

        var homesMapList = plugin.getConfig().getMapList(configHomePath);

        if (homesMapList.isEmpty()) {
            player.sendMessage("You don't have any homes.");
        }

        StringBuilder sb = new StringBuilder();

        homesMapList.forEach((map) -> {
            sb.append(map.keySet()).append(", ");
        });

        player.sendMessage("homes: " + sb);

        return true;
    }
}
