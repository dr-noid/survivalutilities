package com.ebkir.survivalutilities.commands;

import com.ebkir.survivalutilities.utils.Messager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class BaseCommand extends Command {

    private boolean playerOnly;
    private int minArgs;
    private int maxArgs;

    public BaseCommand(@NotNull String name, boolean playerOnly, int minArgs, int maxArgs) {
        super(name);

        this.minArgs = minArgs;
        this.maxArgs = maxArgs;
        this.playerOnly = playerOnly;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return super.tabComplete(sender, alias, args);
    }

    public abstract boolean command(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args);

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        // Player only check
        if (playerOnly && senderNotPlayer(sender)) {
            Messager.onlyPlayerCommand(sender);
            return true;
        }

        // Correct number of arguments check
        if (!(args.length >= minArgs && args.length <= maxArgs)) {
            Messager.send(sender, this.getUsage());
            return true;
        }

        // Execute the command
        return this.command(sender, commandLabel, args);
    }

    private boolean senderNotPlayer(CommandSender sender) {
        return !(sender instanceof Player);
    }

    public Player getPlayer(CommandSender sender) {
        if (sender instanceof Player player) return player;
        return null;
    }
}
