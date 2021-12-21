package com.ebkir.survivalutilities.commands.spawn;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.commands.BaseCommand;
import com.ebkir.survivalutilities.utils.Messager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSpawnCommand extends BaseCommand {

    private final SurvivalUtilities plugin;
    private final String rootPath;

    public SetSpawnCommand(SurvivalUtilities plugin, String rootPath) {
        super("setspawn", true, 0, 0);

        this.plugin = plugin;
        this.rootPath = rootPath;
    }

    @Override
    public boolean command(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            Messager.send(sender, "&cOnly players can use this command");
            return true;
        }

        if (args.length != 0) {
            Messager.send(sender, super.getUsage());
            return true;
        }

        Location loc = player.getLocation();

        plugin.getConfig().set(rootPath, loc);
        plugin.saveConfig();

        plugin.getLogger().info("&aSet new spawn at: " + loc);

        Messager.send(player, "&aNew spawn location has been set");
        return true;
    }
}
