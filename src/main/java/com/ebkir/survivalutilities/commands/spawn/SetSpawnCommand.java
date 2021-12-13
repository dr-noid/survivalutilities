package com.ebkir.survivalutilities.commands.spawn;

import com.ebkir.survivalutilities.SurvivalUtilities;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSpawnCommand implements CommandExecutor {

    private final SurvivalUtilities plugin;
    private final String rootPath;

    public SetSpawnCommand(SurvivalUtilities plugin, String rootPath) {
        this.plugin = plugin;
        this.rootPath = rootPath;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return false;
        }

        Location loc = player.getLocation();

        plugin.getLogger().info("Adding spawn loc to config...");
        plugin.getLogger().info(loc.toString());

        plugin.getConfig().set(rootPath, loc);
        plugin.saveConfig();

        player.sendMessage("New spawn location has been set!");
        return true;
    }
}
