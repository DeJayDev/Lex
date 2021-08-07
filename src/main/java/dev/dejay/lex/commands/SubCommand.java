package dev.dejay.lex.commands;

import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SubCommand extends BaseCommand {

    public SubCommand(JavaPlugin plugin, String name) {
        super(plugin, name);
        unregister();
    }

    public SubCommand(JavaPlugin plugin, String name, String description) {
        super(plugin, name, description);
        unregister();
    }

    public SubCommand(JavaPlugin plugin, String name, String description, String permission) {
        super(plugin, name, description, permission);
        unregister();
    }

    public SubCommand(JavaPlugin plugin, String name, String description, String permission, String... aliases) {
        super(plugin, name, description, permission, aliases);
        unregister();
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return super.onTabComplete(sender, alias, args);
    }

}
