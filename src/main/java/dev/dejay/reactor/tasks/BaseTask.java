package dev.dejay.reactor.tasks;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BaseTask extends BukkitRunnable {

    private final JavaPlugin plugin;

    protected BaseTask(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
    }

    protected JavaPlugin getPlugin() {
        return plugin;
    }

}
