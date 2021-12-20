package com.ebkir.survivalutilities.utils;

import com.ebkir.survivalutilities.SurvivalUtilities;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messager {

    public static void send(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r" + msg));
    }

    public static void sendPvpStatus(CommandSender sender) {
        if (SurvivalUtilities.isPvp()) {
            send(sender, "PvP is &cenabled");
            return;
        }
        send(sender, "PvP is &adisabled");
    }
}
