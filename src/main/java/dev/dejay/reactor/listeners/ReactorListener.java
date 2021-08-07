package dev.dejay.reactor.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public interface ReactorListener extends org.bukkit.event.Listener {

    default void startListening(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    default void stopListening() {
        HandlerList.unregisterAll(this);
    }

}
