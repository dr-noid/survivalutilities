package com.ebkir.survivalutilities.commands.spawn;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.commands.BaseCommand;
import com.ebkir.survivalutilities.utils.Messager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand extends BaseCommand {

    private final SurvivalUtilities plugin;
    private final String rootPath;

    public SpawnCommand(SurvivalUtilities plugin, String rootPath) {
        super("spawn", true, 0, 0);
        super.setUsage("/spawn");
        super.setDescription("Go to spawn");

        this.plugin = plugin;
        this.rootPath = rootPath;
    }

    @Override
    public boolean command(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Player player = getPlayer(sender);

        Location loc = plugin.getConfig().getLocation(rootPath);

        if (loc == null) {
            Messager.send(player, "&cSpawn does not exist");
            return true;
        }

        player.teleportAsync(loc);
        Messager.send(player, "&aTeleporting to spawn");
        return true;
    }
}
