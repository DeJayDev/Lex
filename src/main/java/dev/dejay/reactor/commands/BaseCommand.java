package dev.dejay.reactor.commands;

import dev.dejay.reactor.annotations.PlayerOnly;
import dev.dejay.reactor.chat.ChatUtil;
import dev.dejay.reactor.utils.BukkitTools;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BaseCommand extends Command {

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

        TextComponent response = run(plugin, sender, args);
        ChatUtil.getAudience(plugin)
            .sender(sender)
            .sendMessage(response);
        return true;
    }

    public TextComponent run(JavaPlugin plugin, CommandSender sender, String[] args) {
        return Component.empty();
    }

    public void doRegister() throws ReflectiveOperationException {
        final SimpleCommandMap commandMap = BukkitTools.getCommandMap(plugin);
        commandMap.register(getName(), plugin.getName().toLowerCase(), this);
    }

    public void register() {
        try {
            doRegister();
        } catch (Throwable exception) {
            exception.printStackTrace();
        }
    }

    public void error(CommandSender sender, String message) {
        ChatUtil.getAudience(plugin).sender(sender).sendMessage(errorMessage(message));
    }

    public TextComponent errorMessage(String message) {
        return Component.text(message, ChatUtil.getErrorStyle());
    }

    public void success(CommandSender sender, String message) {
        ChatUtil.getAudience(plugin).sender(sender).sendMessage(successMessage(message));
    }

    public TextComponent successMessage(String message) {
        return Component.text(message, ChatUtil.getSuccessStyle());
    }

}
