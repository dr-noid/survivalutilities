package com.ebkir.survivalutilities.commands.pvp;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.utils.Messager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PvpCommand extends Command {

    private final SurvivalUtilities plugin;
    private final String configPath;

    public PvpCommand(SurvivalUtilities plugin, String configPath) {
        super("pvp");
        this.plugin = plugin;
        this.configPath = configPath;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {

        if (args.length != 0 || !sender.isOp()) {
            return true;
        }

        boolean currentState = SurvivalUtilities.isPvp();

        SurvivalUtilities.setPvp(!currentState);
        plugin.getConfig().set(configPath, !currentState);

        Messager.sendPvpStatus(sender);
        return true;
    }
}
