package com.ebkir.survivalutilities.commands.home;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.models.Home;
import com.ebkir.survivalutilities.utils.Messager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DelHomeCommand extends Command {

    private final SurvivalUtilities plugin;
    private final String configRoot;

    public DelHomeCommand(SurvivalUtilities plugin, String configRoot) {
        super("delhome");
        String description = "Delete a home";
        String usageMessage = "&a/delhome <name>";
        super.setDescription(description);
        super.setUsage(usageMessage);

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

        Home removeHome = optionalHome.get();
        homeList.remove(removeHome);
        Location oldLoc = optionalHome.get().getLocation();
        Messager.send(player, "&aDeleted home &3&l" + homeName + "&r&a with coordinates\n" +
                "x: &3&l" + (int) oldLoc.getX() +
                "\n&ay: &3&l" + (int) oldLoc.getY() +
                "\n&az: &3&l" + (int) oldLoc.getZ());
        saveHomeList(homeList, playerConfigRoot);
        return true;
    }

    private void saveHomeList(List<Home> homeList, String playerConfig) {
        plugin.getConfig().set(playerConfig, homeList);
        plugin.saveConfig();
    }
}
