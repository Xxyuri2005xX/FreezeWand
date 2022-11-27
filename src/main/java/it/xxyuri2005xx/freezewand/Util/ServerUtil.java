package it.xxyuri2005xx.freezewand.Util;

import org.bukkit.ChatColor;

public class ServerUtil {

    public static String color(String msg) {
        return (msg == null) ? " " : ChatColor.translateAlternateColorCodes('&', msg);
    }
}
