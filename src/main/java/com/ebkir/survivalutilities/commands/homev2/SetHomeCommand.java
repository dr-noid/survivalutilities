package com.ebkir.survivalutilities.commands.homev2;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.models.Home;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SetHomeCommand extends Command {

    private final SurvivalUtilities plugin;
    private final String configRoot;

    public SetHomeCommand(SurvivalUtilities plugin, String configRoot) {
        super("sethome");
        String description = "Teleport to a home";
        String usageMessage = MessageFormat.format("/<{0}> <name>", super.getName());
        super.setUsage(usageMessage);
        super.setDescription(description);

        this.configRoot = configRoot;
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (args.length != 1) {
            return false;
        }

        String homeName = args[0];
        String playerConfigRoot = configRoot + player.getUniqueId();
        Location playerLocation = player.getLocation();
        var homeToBeAdded = new Home(homeName, playerLocation, player.getUniqueId());

        List<Home> configHomeList = (List<Home>) plugin.getConfig().getList(playerConfigRoot);

        if (configHomeList == null) {
            List<Home> homeList = new ArrayList<>();

            homeList.add(homeToBeAdded);

            plugin.getConfig().set(playerConfigRoot, homeList);
            plugin.saveConfig();
            return true;
        }
        else {
            configHomeList.add(homeToBeAdded);
            plugin.getConfig().set(playerConfigRoot, configHomeList);
            plugin.saveConfig();
        }

//        Home home = new Home(homeName, player.getLocation(), player.getUniqueId());
//
//        List<Home> homeList = new ArrayList<>();
//        homeList.add(home);
//
//        plugin.getLogger().info(home.toString());
//
//        plugin.getConfig().set(configRoot + player.getUniqueId(), homeList);
//        plugin.saveConfig();
//        player.sendMessage("Home " + homeName + " is saved.");
//
//        return false;
        return false;
    }
}
