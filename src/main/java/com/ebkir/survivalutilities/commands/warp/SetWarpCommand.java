package com.ebkir.survivalutilities.commands.home;

import com.ebkir.survivalutilities.SurvivalUtilities;
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

public class SetWarpCommand extends Command {

    private final SurvivalUtilities plugin;
    private final String configRoot;

    public SetWarpCommand(SurvivalUtilities plugin, String configRoot) {
        super("setwarp");
        super.setDescription("Set a warp");
        super.setUsage("&a/setwarp <name>");


        this.configRoot = configRoot;
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

        String warpName = args[0];

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
                Messager.send(player, MessageFormat.format("&cWarp &3&l{0}&r&c already exists", warpName));
                return true;
            }
        }

        Messager.send(player, MessageFormat.format("&aWarp &3&l{0}&r&a has been set", warpName));
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
