package dev.dejay.lex.utils;

import com.destroystokyo.paper.profile.CraftPlayerProfile;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.dejay.lex.utils.api.TexturedPlayer;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket.Action;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket.RelativeArgument;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerUtils {

    private static JavaPlugin plugin;
    public PlayerUtils(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void setName(Player player, String name) throws NoSuchFieldException {
        if(name.length() > 16) name = name.substring(0, 16);

        ServerPlayer entity = ((CraftPlayer) player).getHandle();

        CraftPlayerProfile profile = new CraftPlayerProfile(player.getUniqueId(), name);
        profile.setName(name);

        player.playerListName(player.playerListName());
    }

    public static void setSkin(Player player, TexturedPlayer skin) throws NoSuchFieldException {
        Property prop = new Property(
            "textures", skin.texture(), skin.signature());

        setName(player, skin.displayName());

        CraftPlayer craftPlayer = (CraftPlayer) player;

        craftPlayer
            .getProfile()
            .getProperties()
            .clear();
        craftPlayer
            .getProfile()
            .getProperties()
            .put("textures", prop);

        ServerPlayer entity = craftPlayer.getHandle();
        CraftWorld world = entity.getLevel().getWorld(); // yay nms

        List<Packet> packets = Arrays.asList(
            new ClientboundPlayerInfoPacket(
                Action.REMOVE_PLAYER, entity
            ),
            new ClientboundPlayerInfoPacket(
                Action.ADD_PLAYER, entity
            ),
            new ClientboundRespawnPacket(
                world.getHandle().dimensionType(),
                world.getHandle().dimension(),
                world.getHandle().getSeed(),
                entity.gameMode.getGameModeForPlayer(),
                entity.gameMode.getPreviousGameModeForPlayer(),
                world.getHandle().isDebug(),
                world.getHandle().isFlat(),
                false
            ),
            new ClientboundPlayerAbilitiesPacket(
                entity.getAbilities()
            ),
            new ClientboundPlayerPositionPacket(
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                entity.getYHeadRot(),
                entity.getXRot(),
                new HashSet<>(),
                0,
                entity.isPassenger()
            ),
            new ClientboundSetCarriedItemPacket(player.getInventory().getHeldItemSlot())
        );

        packets.forEach(packet -> entity.connection.send(packet));

        player.setFlying(player.isFlying());
        player.setExhaustion(entity.getFoodData().getExhaustionLevel());
        player.updateInventory();

        for (Player target : plugin.getServer().getOnlinePlayers()) {
            target.hidePlayer(plugin, player);
            target.showPlayer(plugin, player);
        }
    }

}
