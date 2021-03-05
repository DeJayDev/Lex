package dev.dejay.reactor.chat;

import static net.kyori.adventure.text.format.NamedTextColor.GRAY;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.RED;
import static net.kyori.adventure.text.format.TextDecoration.ITALIC;

import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatUtil {

    private static BukkitAudiences bukkitAudience;

    @Getter
    public static final Style successStyle = Style.style(GREEN);
    @Getter
    public static final Style hintStyle = Style.style(GRAY, ITALIC);
    @Getter
    public static final Style errorStyle = Style.style(RED);

    public static BukkitAudiences getAudience(JavaPlugin plugin) {
        bukkitAudience = BukkitAudiences.create(plugin);
        return bukkitAudience;
    }

    public static Component formatPlayerName(Player player, Boolean grammar) {
        return formatName(player, grammar);
    }

    public static Component formatName(CommandSender target, Boolean grammar) {
        if(!(target instanceof Player)) {
            return Component.text(target.getName()); // If they're not human, fuck it.
        }

        Player player = (Player) target;

        if(!grammar) {
            return player.displayName();
        }

        if(player.displayName().toString().endsWith("s")) {
            return player.displayName().append(Component.text("'"));
        } else {
            return player.displayName().append(Component.text("'s"));
        }
    }

}
