package dev.dejay.reactor.items.types;

import com.destroystokyo.paper.profile.PlayerProfile;
import dev.dejay.reactor.Reactor;
import dev.dejay.reactor.items.ReactorItem;
import dev.dejay.reactor.utils.AshconPlayerAPI;
import dev.dejay.reactor.utils.CraftheadPlayerAPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

@Data
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class SkullItem extends ReactorItem {

    private String owner;

    @Override
    public ItemStack toItemStack() {
        ItemStack stack = super.toItemStack();
        if (stack.getItemMeta() != null) {
            ItemMeta meta = stack.getItemMeta();
            try {
                PlayerProfile craftheadProfile = Bukkit.getServer().createProfile(owner);
                craftheadProfile.setProperty(CraftheadPlayerAPI.getProfile(owner));
                ((SkullMeta) meta).setPlayerProfile(craftheadProfile);
            } catch (Exception e) {
                Reactor.getLogger().error("Failed to get profile for " + owner + "from Crafthead.");
                Reactor.getLogger().error("Trying another way...");
                try {
                    PlayerProfile ashconProfile = Bukkit.getServer().createProfile(owner);
                    ashconProfile.setProperty(AshconPlayerAPI.getProfile(owner));
                    ((SkullMeta) meta).setPlayerProfile(ashconProfile);
                } catch (Exception e2) {
                    Reactor.getLogger().error("Failed to get profile for " + owner + "from Ashcon.");
                    Reactor.getLogger().error("Using Vanilla instead... this method sucks.");
                    PlayerProfile vanillaProfile = Bukkit.getServer().createProfile(owner);
                    vanillaProfile.complete();
                    ((SkullMeta) meta).setPlayerProfile(vanillaProfile);
                }
            }

            stack.setItemMeta(meta);
        }
        return stack;
    }

    public static SkullItem of(ItemStack itemStack) {
        ReactorItem item = ReactorItem.of(itemStack);
        if (itemStack.getItemMeta() != null) {
            ((SkullItem) item).setOwner(((SkullMeta) itemStack.getItemMeta()).getOwner());
        }
        return (SkullItem) item;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends ReactorItem.Builder<SkullItem, Builder> {
        protected Builder() {
        }

        protected Builder(SkullItem item) {
            super(item);
        }

        @Override
        protected SkullItem init() {
            return new SkullItem();
        }

        @Override
        public SkullItem build() {
            return super.build();
        }

        public Builder owner(String owner) {
            item.owner = owner;
            return this;
        }

    }

}
