package dev.dejay.lex.commands;

import dev.dejay.lex.annotations.PlayerOnly;
import dev.dejay.lex.chat.ChatUtil;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BaseCommand extends Command {

    private final List<SubCommand> subCommands = new ArrayList<>();

    @Getter
    private final JavaPlugin plugin;

    public BaseCommand(JavaPlugin plugin, String name) {
        super(name);
        this.plugin = plugin;
        register();
    }

    public BaseCommand(JavaPlugin plugin, String name, String description) {
        super(name, description != null ? description : "", "/" + name, Collections.emptyList());
        this.plugin = plugin;
        register();
    }

    public BaseCommand(JavaPlugin plugin, String name, String description, String permission) {
        super(name, description != null ? description : "", "/" + name, Collections.emptyList());
        this.plugin = plugin;
        setPermission(permission);
        register();
    }

    public BaseCommand(JavaPlugin plugin, String name, String description, String permission, String... aliases) {
        super(name, description != null ? description : "", "/" + name, Arrays.asList(aliases));
        this.plugin = plugin;
        setPermission(permission);
        register();
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    public void addSubCommand(SubCommand sub) {
        subCommands.add(sub);
    }

    public void addSubCommands(SubCommand... subs) {
        Collections.addAll(subCommands, subs);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String command, String[] args) {
        Class<?> objectClass = getClass();

        for (Method method : objectClass.getMethods()) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(PlayerOnly.class)) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "This command can only be ran by players");
                    return true;
                }
            }
        }

        String permission = getPermission();

        if (permission != null && !sender.hasPermission(permission) && !sender.isOp()) {
            error(sender, "You do not have permission to run this command.");
            return true;
        }

        Component response = run(plugin, sender, args);
        sender.sendMessage(response);
        return true;
    }

    public Component run(JavaPlugin plugin, CommandSender sender, String[] args) {
        return Component.empty();
    }

    public void register() {
        try {
            final CommandMap commandMap = Bukkit.getCommandMap();
            commandMap.register(getName(), plugin.getName().toLowerCase(), this);
        } catch (Throwable exception) {
            exception.printStackTrace();
        }
    }

    public void unregister() {
        final CommandMap commandMap = Bukkit.getCommandMap();
        if(commandMap.getCommand(getName()) != null) {
            this.unregister(commandMap);
        }
    }

    public void error(CommandSender sender, String message) {
        sender.sendMessage(errorMessage(message));
    }

    public Component errorMessage(String message) {
        return Component.text(message, ChatUtil.getErrorStyle());
    }

    public void success(CommandSender sender, String message) {
        sender.sendMessage(successMessage(message));
    }

    public Component successMessage(String message) {
        return Component.text(message, ChatUtil.getSuccessStyle());
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return super.tabComplete(sender, alias, args, null);
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        List<String> subs /* :flushed: */ = new ArrayList<>();
        if(subCommands.size() == 0) return subs;
        if (args.length == 1) {
            for (SubCommand subCommand : subCommands) {
                subs.add(subCommand.getName());
            }
        }
        return subs;
    }

}
