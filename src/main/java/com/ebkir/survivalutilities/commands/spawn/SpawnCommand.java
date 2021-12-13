package com.ebkir.survivalutilities.commands.spawn;

import com.ebkir.survivalutilities.SurvivalUtilities;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {

    private final SurvivalUtilities plugin;
    private final String rootPath;

    public SpawnCommand(SurvivalUtilities plugin, String rootPath) {
        this.plugin = plugin;
        this.rootPath = rootPath;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return false;
        }

        Location loc = plugin.getConfig().getLocation(rootPath);

        if (loc == null) {
            sender.sendMessage("Spawn does not exist yet, try /setspawn");
            return true;
        }

        player.teleport(loc);
        return true;
    }
}
