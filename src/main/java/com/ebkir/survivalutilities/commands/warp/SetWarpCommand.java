package com.ebkir.survivalutilities.commands.warp;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.commands.BaseCommand;
import com.ebkir.survivalutilities.models.Warp;
import com.ebkir.survivalutilities.utils.Messager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SetWarpCommand extends BaseCommand {

    private final SurvivalUtilities plugin;
    private final String configRoot;

    public SetWarpCommand(SurvivalUtilities plugin, String configRoot) {
        super("setwarp", true, 1, 1);
        super.setDescription("Set a warp");
        super.setUsage("&a/setwarp <name>");

        this.configRoot = configRoot;
        this.plugin = plugin;
    }

    @Override
    public boolean command(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        String warpName = args[0];

        Player player = getPlayer(sender);
        Location playerLocation = player.getLocation();

        Warp warpToBeAdded = new Warp(warpName, playerLocation, player.getUniqueId());

        List<Warp> configWarpList = (List<Warp>) plugin.getConfig().getList(configRoot);

        if (configWarpList == null) {
            // add new warp if list is empty
            saveNewWarpList(warpToBeAdded);
        }
        else {
            // else we just append to the existing warpList
            boolean warpAdded = addWarp(warpToBeAdded, configWarpList);

            if (!warpAdded) {
                String warpAlreadyExistsMessage = "&cWarp &3&l" + warpName + "&r&c already exists";
                Messager.send(player, warpAlreadyExistsMessage);
                return true;
            }
        }
        String warpSetMessage = "&aWarp &3&l" + warpName + "&r&a has been set";
        Messager.send(player, warpSetMessage);
        return true;
    }

    /**
     * For creating a warp entry
     * @param warp Warp object to be added to the config
     */
    private void saveNewWarpList(Warp warp) {
        List<Warp> warpList = new ArrayList<>();
        warpList.add(warp);
        plugin.getConfig().set(configRoot, warpList);
        plugin.saveConfig();
    }

    /**
     * For adding warps
     * @return Returns <code>true</code> if the warp is saved and <code>false</code>
     * if a warp with the same name already exists
     */
    private boolean addWarp(Warp warp, List<Warp> warpList) {
        if (warpAlreadyExists(warp, warpList)) {
            return false;
        }
        warpList.add(warp);
        plugin.getConfig().set(configRoot, warpList);
        plugin.saveConfig();
        return true;
    }

    /**
     * Check if warp already exists.
     * @param warp
     * @param warpList
     * @return returns <code>true</code> if a warp with the
     * same name exists in the given list.
     */
    private boolean warpAlreadyExists(Warp warp, List<Warp> warpList) {
        return warpList
                .stream()
                .anyMatch(w -> w.getName().equals(warp.getName()));
    }
}
