package com.ebkir.survivalutilities.commands.home;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.models.Home;
import com.ebkir.survivalutilities.utils.Messager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.StringJoiner;

public class HomesCommand extends Command {

    private final SurvivalUtilities plugin;
    private final String configRoot;

    public HomesCommand(SurvivalUtilities plugin, String configRoot) {
        super("homes");
        String description = "See all your homes";
        String usageMessage = "&a/homes";
        super.setUsage(usageMessage);
        super.setDescription(description);

        this.configRoot = configRoot;
        this.plugin = plugin;
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

        String playerConfig = configRoot + player.getUniqueId();

        List<Home> homeList = getHomes(playerConfig);
        if (homeList == null || homeList.isEmpty()) {
            Messager.send(player, "&aYou don't have any homes");
            return true;
        }

        sendPlayerHomeList(player, homeList);
        return true;
    }

    private void sendPlayerHomeList(Player player, List<Home> homeList) {
        List<String> homeNameList = homeList
                .stream()
                .map(Home::getName)
                .toList();

        if (homeNameList.size() == 0) {
            return;
        }
        if (homeNameList.size() == 1) {
            Messager.send(player, "&aHome: &a&l" + homeNameList.get(0));
            return;
        }

        var stringJoiner = new StringJoiner("&r&a, &3&l", "&3&l", "");
        homeNameList.forEach(stringJoiner::add);
        Messager.send(player, "&aHomes: " + stringJoiner);

    }

    /**
     * gets all homes from a player from the config.yml
     * @param playerConfig
     * @return <code>List</code> of Homes or <code>null</code> if the player doesn't have any homes.
     */
    private @Nullable List<Home> getHomes(String playerConfig) {
        List<Home> configHomeList = (List<Home>) plugin.getConfig().getList(playerConfig);

        if (configHomeList == null) {
            return null;
        }

        return configHomeList;
    }
}
