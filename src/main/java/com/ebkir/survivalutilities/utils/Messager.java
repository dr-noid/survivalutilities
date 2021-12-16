package com.ebkir.survivalutilities.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messager {

    public static void send(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r" + msg));
    }
}
