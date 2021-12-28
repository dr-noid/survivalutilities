package com.ebkir.survivalutilities.commands.warp;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.commands.BaseCommand;
import com.ebkir.survivalutilities.models.Warp;
import com.ebkir.survivalutilities.utils.Messager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DelWarpCommand extends BaseCommand {

    private final SurvivalUtilities plugin;
    private final String configRoot;

    public DelWarpCommand(SurvivalUtilities plugin, String configRoot) {
        super("delwarp", true, 1, 1);
        super.setDescription("Delete a warp");
        super.setUsage("&a/delwarp <name>");

        this.configRoot = configRoot;
        this.plugin = plugin;
    }

    @Override
    public boolean command(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Player player = getPlayer(sender);

        String warpName = args[0];
        @SuppressWarnings("unchecked")
        List<Warp> warpList = (List<Warp>) plugin.getConfig().getList(configRoot);

        if (warpList == null) {
            Messager.send(player, "&aNo warps available");
            return true;
        }

        var optionalWarp = warpList
                .stream()
                .filter(warp -> warp.getName().equals(warpName))
                .findFirst();

        if (optionalWarp.isEmpty()) {
            Messager.send(player, "&aThere are no warps with this name");
            return true;
        }

        Warp removeWarp = optionalWarp.get();
        warpList.remove(removeWarp);
        Location oldLoc = optionalWarp.get().getLocation();
        Messager.send(player, "&aDeleted warp &3&l" + warpName + "&r&a with coordinates\n" +
                "x: &3&l" + (int) oldLoc.getX() +
                "\n&ay: &3&l" + (int) oldLoc.getY() +
                "\n&az: &3&l" + (int) oldLoc.getZ());
        saveWarpList(warpList);
        return true;
    }

    private void saveWarpList(List<Warp> warpList) {
        plugin.getConfig().set(configRoot, warpList);
        plugin.saveConfig();
    }
}
