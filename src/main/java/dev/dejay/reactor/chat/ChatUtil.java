package dev.dejay.reactor.chat;

import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.format.Style;
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


}
