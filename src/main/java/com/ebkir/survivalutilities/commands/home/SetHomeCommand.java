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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SetHomeCommand extends BaseCommand {

    private final SurvivalUtilities plugin;
    private final String configRoot;

    public SetHomeCommand(SurvivalUtilities plugin, String configRoot) {
        super("sethome", true, 1, 1);
        String description = "Teleport to a home";
        String usageMessage = "&a/sethome <name>";
        super.setUsage(usageMessage);
        super.setDescription(description);

        this.configRoot = configRoot;
        this.plugin = plugin;
    }

    @Override
    public boolean command(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Player player = getPlayer(sender);

        String homeName = args[0];
        String playerConfigRoot = configRoot + player.getUniqueId();
        Location playerLocation = player.getLocation();
        var homeToBeAdded = new Home(homeName, playerLocation, player.getUniqueId());

        List<Home> configHomeList = (List<Home>) plugin.getConfig().getList(playerConfigRoot);

        if (configHomeList == null) {
            // add new home if list is empty
            saveNewHome(homeToBeAdded, playerConfigRoot);
        }
        else {
            // else we just append to the existing homeList
            plugin.getLogger().info("confighomelist: " + configHomeList);
            boolean homeAdded = addHome(homeToBeAdded, playerConfigRoot, configHomeList);

            if (!homeAdded) {
                Messager.send(player, MessageFormat.format("&cHome &3&l{0}&r&c already exists", homeName));
                return true;
            }
        }

        Messager.send(player, MessageFormat.format("&aHome &3&l{0}&r&a has been set", homeName));
        return true;
    }

    /**
     * For creating a home entry
     * @param home Home object to be added to the config
     * @param playerConfig yml path
     */
    private void saveNewHome(Home home, String playerConfig) {
        List<Home> homeList = new ArrayList<>();
        homeList.add(home);
        plugin.getConfig().set(playerConfig, homeList);
        plugin.saveConfig();
    }

    /**
     * For adding homes
     * @return Returns <code>true</code> if the home is saved and <code>false</code>
     * if a home with the same name already exists
     */
    private boolean addHome(Home home, String playerConfig, List<Home> homeList) {
        if (homeAlreadyExists(home, homeList)) {
            return false;
        }
        homeList.add(home);
        plugin.getConfig().set(playerConfig, homeList);
        plugin.saveConfig();
        return true;
    }

    /**
     * Check if home already exists.
     * @param home
     * @param homeList
     * @return returns <code>true</code> if a home with the
     * same name exists in the given list.
     */
    private boolean homeAlreadyExists(Home home, List<Home> homeList) {
        return homeList
                .stream()
                .anyMatch(h -> h.getName().equals(home.getName()));
    }
}
