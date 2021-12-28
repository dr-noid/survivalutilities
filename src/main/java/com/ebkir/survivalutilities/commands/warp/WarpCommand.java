package com.ebkir.survivalutilities.commands.warp;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.commands.BaseCommand;
import com.ebkir.survivalutilities.models.Home;
import com.ebkir.survivalutilities.models.Warp;
import com.ebkir.survivalutilities.utils.Messager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WarpCommand extends BaseCommand {

    private final SurvivalUtilities plugin;
    private final String configRoot;

    public WarpCommand(SurvivalUtilities plugin, String configRoot) {
        super("warp", true, 1, 1);
        super.setDescription("Teleport to a warp");
        super.setUsage("&a/warp <name>");
        super.setAliases(List.of("w"));

        this.plugin = plugin;
        this.configRoot = configRoot;
    }

    @Override
    public boolean command(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        String warpName = args[0];

        Player player = getPlayer(sender);

        List<Warp> warpList = (List<Warp>) plugin.getConfig().getList(configRoot);

        if (warpList == null) {
            Messager.send(player, "&aThere are no warps");
            return true;
        }

        var optionalHome = warpList
                .stream()
                .filter(warp -> warp.getName().equals(warpName))
                .findFirst();

        if (optionalHome.isEmpty()) {
            Messager.send(player, "&aThere's no warp with this name");
            return true;
        }

        Location location = optionalHome.get().getLocation();
        Messager.send(player, "&aTeleporting to &3&l" + warpName + "&r&a");
        player.teleportAsync(location);
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {

        if (args.length != 1) {
            return super.tabComplete(sender, alias, args);
        }

        List<Warp> warpList = (List<Warp>) plugin.getConfig().getList(configRoot);

        if (warpList == null) {
            return super.tabComplete(sender, alias, args);
        }

        return warpList
                .stream()
                .map(Warp::getName)
                .toList();
    }
}
