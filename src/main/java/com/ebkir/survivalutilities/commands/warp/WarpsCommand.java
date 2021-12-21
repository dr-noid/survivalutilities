package com.ebkir.survivalutilities.commands.warp;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.commands.BaseCommand;
import com.ebkir.survivalutilities.models.Warp;
import com.ebkir.survivalutilities.utils.Messager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.StringJoiner;

public class WarpsCommand extends BaseCommand {

    private final SurvivalUtilities plugin;
    private final String configRoot;

    public WarpsCommand(SurvivalUtilities plugin, String configRoot) {
        super("warps", true, 0, 0);
        super.setDescription("See all warps");
        super.setUsage("&a/warps");

        this.plugin = plugin;
        this.configRoot = configRoot;
    }

    @Override
    public boolean command(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Bukkit.getServer().broadcast(Component.text("Warps' command is now called!"));

        Player player = getPlayer(sender);

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

        String prefix = warpNameList.size() == 1 ? "&aWarp: &3&l" : "&aWarps: ";

//        if (warpNameList.size() == 1) {
//            Messager.send(player, "&aWarp: &3&l" + warpNameList.get(0));
//            return;
//        }

        var stringJoiner = new StringJoiner("&r&a, &3&l", "&3&l", "");
        warpNameList.forEach(stringJoiner::add);
        Messager.send(player, prefix + stringJoiner);
    }

    private List<Warp> getWarps() {
        List<Warp> warpList = (List<Warp>) plugin.getConfig().getList(configRoot);

        if (warpList == null) {
            return null;
        }

        return warpList;
    }
}
