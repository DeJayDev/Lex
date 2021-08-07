package dev.dejay.reactor.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitTools {

    public static SimpleCommandMap getCommandMap(JavaPlugin plugin) throws ReflectiveOperationException {
        return (SimpleCommandMap) plugin.getServer().getClass()
            .getDeclaredMethod("getCommandMap")
            .invoke(plugin.getServer());
    }

    public static CommandMap getCommandMap() {
        return Bukkit.getCommandMap();
    }

}
