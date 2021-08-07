package dev.dejay.lex.listeners;

import org.bukkit.plugin.java.JavaPlugin;

public class BaseListener implements LexListener {

    private final JavaPlugin plugin;

    public BaseListener(JavaPlugin plugin) {
        this.plugin = plugin;

        this.startListening(plugin);
    }

    protected JavaPlugin getPlugin() {
        return plugin;
    }

}
