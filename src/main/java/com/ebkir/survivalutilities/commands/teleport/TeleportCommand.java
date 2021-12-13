package com.ebkir.survivalutilities.commands.teleport;

import com.ebkir.survivalutilities.SurvivalUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportCommand implements CommandExecutor {

    private final SurvivalUtilities plugin;

    public TeleportCommand(SurvivalUtilities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length != 1) {
            return false;
        }

        String target = args[0];
        Player targetPlayer = Bukkit.getPlayer(target);

        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Die player is niet online");
            return true;
        }
        player.teleport(targetPlayer.getLocation());
        return true;
    }
}
