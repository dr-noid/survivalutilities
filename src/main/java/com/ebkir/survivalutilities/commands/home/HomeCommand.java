package com.ebkir.survivalutilities.commands.home;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.commands.BaseCommand;
import com.ebkir.survivalutilities.models.Home;
import com.ebkir.survivalutilities.utils.Messager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeCommand extends BaseCommand {

    private final SurvivalUtilities plugin;
    private final String configRoot;

    public HomeCommand(SurvivalUtilities plugin, String configRoot) {
        super("home", true, 1, 1);
        String description = "Teleport to a home";
        String usageMessage = "&a/home <name>";
        super.setUsage(usageMessage);
        super.setDescription(description);
        super.setAliases(List.of("h"));

        this.configRoot = configRoot;
        this.plugin = plugin;
    }

    @Override
    public boolean command(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Player player = getPlayer(sender);

        String homeName = args[0];
        String playerConfigRoot = configRoot + player.getUniqueId();
        List<Home> homeList = (List<Home>) plugin.getConfig().getList(playerConfigRoot);


        if (homeList == null) {
            Messager.send(player, "&aYou don't have any homes");
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

        Location location = optionalHome.get().getLocation();
        Messager.send(player, "&aTeleporting to &3&l" + homeName + "&r&a");
        player.teleportAsync(location);
        return true;
    }
}
