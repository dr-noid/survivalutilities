package com.ebkir.survivalutilities.commands.warp;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.models.Home;
import com.ebkir.survivalutilities.models.Warp;
import com.ebkir.survivalutilities.utils.Messager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.StringJoiner;

public class WarpsCommand extends Command {

    private final SurvivalUtilities plugin;
    private final String configRoot;

    public WarpsCommand(SurvivalUtilities plugin, String configRoot) {
        super("warps");
        super.setDescription("See all warps");
        super.setUsage("&a/warps");

        this.plugin = plugin;
        this.configRoot = configRoot;
    }
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            Messager.send(sender, "&cOnly players can use this command");
            return true;
        }

        if (args.length != 0) {
            Messager.send(sender, super.getUsage());
            return true;
        }

        List<Warp> warpList = getWarps();
        if (warpList == null || warpList.isEmpty()) {
            Messager.send(player, "&aThere are no warps");
            return true;
        }

        sendPlayerWarpList(player, warpList);
        return true;
    }

    private void sendPlayerWarpList(Player player, List<Warp> warpList) {
        List<String> warpNameList = warpList
                .stream()
                .map(Warp::getName)
                .toList();

        if (warpNameList.size() == 0) {
            return;
        }
        if (warpNameList.size() == 1) {
            Messager.send(player, "&aHome: &a&l" + warpNameList.get(0));
            return;
        }

        var stringJoiner = new StringJoiner("&a, ", "&3&l", "");
        warpNameList.forEach(stringJoiner::add);
        Messager.send(player, "&aHomes: " + stringJoiner);
    }

    private List<Warp> getWarps() {
        List<Warp> warpList = (List<Warp>) plugin.getConfig().getList(configRoot);

        if (warpList == null) {
            return null;
        }

        return warpList;
    }
}
