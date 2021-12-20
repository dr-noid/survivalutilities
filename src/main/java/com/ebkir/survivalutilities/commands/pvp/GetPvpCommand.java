package com.ebkir.survivalutilities.commands.pvp;

import com.ebkir.survivalutilities.SurvivalUtilities;
import com.ebkir.survivalutilities.utils.Messager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GetPvpCommand extends Command {

    private final SurvivalUtilities plugin;
    private final String configPath;

    public GetPvpCommand(SurvivalUtilities plugin, String configPath) {
        super("getpvp");
        this.plugin = plugin;
        this.configPath = configPath;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {

        if (args.length != 0) {
            return true;
        }

        Messager.sendPvpStatus(sender);

        return true;
    }
}
