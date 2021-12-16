package com.ebkir.survivalutilities.commands.homev2;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.models.Home;
import com.ebkir.survivalutilities.utils.Messager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

public class HomeCommand extends Command {

    private final SurvivalUtilities plugin;
    private final String configRoot;

    public HomeCommand(SurvivalUtilities plugin, String configRoot) {
        super("home");
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
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Wrong arguments");
            return false;
        }
        String homeName = args[0];
        String playerConfigRoot = configRoot + player.getUniqueId();
        List<Home> homeList = (List<Home>) plugin.getConfig().getList(playerConfigRoot);

        if (homeList == null) {
            Messager.send(player, "&aYou don't have any homes.");
            return true;
        }

        var optionalHome = homeList
                .stream()
                .filter(home -> home.getName().equals(homeName))
                .findFirst();

        if (optionalHome.isEmpty()) {
            Messager.send(player, "&aYou don't have a home with this name");
            return true;
        }

        return false;
    }

//
//        String homeName = args[0];
//        String playerConfigRoot = homeName + player.getUniqueId();
//        var homesMapList = plugin.getConfig().getMapList(playerConfigRoot);
//
//        if (homesMapList.isEmpty()) {
//            player.sendMessage("You don't have any homes yet");
//            return true;
//        }
//
//        var optionalHome =
//                homesMapList.stream()
//                        .filter((map) -> map.containsKey(homeName))
//                        .toList();
//
//        if (optionalHome.isEmpty()) {
//            player.sendMessage("You don't have a home with this name.");
//            return true;
//        }
//
//        Location homeLocation = (Location) optionalHome.get(0).get(homeName);
//        player.teleport(homeLocation);
//        return true;
//    }
}
