package dev.dejay.lex.chat;

import static net.kyori.adventure.text.format.NamedTextColor.GRAY;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.RED;
import static net.kyori.adventure.text.format.TextDecoration.ITALIC;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtil {

    @Getter
    private static final Style successStyle = Style.style(GREEN);
    @Getter
    private static final Style hintStyle = Style.style(GRAY, ITALIC);
    @Getter
    private static final Style errorStyle = Style.style(RED);

    public static Component formatPlayerName(CommandSender target, boolean grammar) {
        if (!(target instanceof Player)) {
            return Component.text(target.getName());
        }

        Player player = (Player) target;

        if (!grammar) {
            return player.displayName();
        }

        if (player.displayName().toString().endsWith("s")) {
            return player.displayName().append(Component.text("'"));
        } else {
            return player.displayName().append(Component.text("'s"));
        }
    }

}
