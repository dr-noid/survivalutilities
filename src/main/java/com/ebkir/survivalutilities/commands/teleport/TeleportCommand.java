package com.ebkir.survivalutilities.commands.teleport;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.utils.Messager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class TeleportCommand extends Command {

    private final SurvivalUtilities plugin;

    public TeleportCommand(SurvivalUtilities plugin) {
        super("teleport");
        super.setUsage("/teleport <name>");
        super.setDescription("Teleport to a player");

        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            Messager.send(sender, "&cOnly players can use this command");
            return true;
        }

        if (args.length != 1) {
            Messager.send(sender, super.getUsage());
            return true;
        }

        String targetUser = args[0];
        Player targetPlayer = Bukkit.getPlayer(targetUser);

        if (targetPlayer == null) {
            Messager.send(player, "&cThat player doesn't exist");
            return true;
        }

        if (targetPlayer.isOnline()) {
            Messager.send(player, "&aTeleporting to &3&l" + targetPlayer.getName());
            Messager.send(targetPlayer, "&3&l" + player.getName() + "&r&a is teleporting to you");
            player.teleportAsync(targetPlayer.getLocation());
        }
        else {
            Messager.send(player, "&3&l" + targetPlayer.getName() + "&r&a is not online");
        }
        return true;
    }
}
