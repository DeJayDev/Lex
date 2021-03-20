/*
 * Copyright (c) inventivetalent.
 *
 * In the spirit of open source, her copyright will be maintained here,
 * even though I'm not sure what the hell I'm doing.
 */

package dev.dejay.reactor.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

@Data
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ReactorItem extends AbstractItem {

    private static final Map<Class<? extends ItemStack>, Class<? extends ReactorItem>> metaMap = new HashMap<>();
    private static final Map<String, Class<? extends ReactorItem>> craftMetaMap = new HashMap<>();

    @NotNull private Material type = Material.AIR; // "null"
    private int amount = 1;
    private short durability = 0;

    private Component displayName = Component.empty();
    @Singular private List<Component> lore = new ArrayList<>();
    @Singular private Map<Enchantment, Integer> enchants = new HashMap<>();
    @Singular private List<ItemFlag> flags = new ArrayList<>();
    private boolean unbreakable = false;

    public ReactorItem(@NotNull Material type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public ReactorItem(@NotNull Material type, int amount, short durability) {
        this.type = type;
        this.amount = amount;
        this.durability = durability;
    }

    public ReactorItem(@NotNull Material type, int amount, short durability, Component displayName) {
        this.type = type;
        this.amount = amount;
        this.durability = durability;
        this.displayName = displayName;
    }

    public ReactorItem(ReactorItem other) {
        this(other.type, other.amount, other.durability, other.displayName, other.lore, other.enchants, other.flags, other.unbreakable);
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(type, amount);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            if(displayName != Component.empty()) {
                meta.displayName(displayName);
            }
            meta.lore(lore);
            enchants.forEach((key, value) -> meta.addEnchant(key, value, true));
            flags.forEach(meta::addItemFlags);
        }
        return itemStack;
    }

    public Builder modify() {
        try {
            ItemMeta meta = Bukkit.getItemFactory().getItemMeta(type);
            if(craftMetaMap.containsKey(meta.getClass().getSimpleName())) {
                return (Builder) craftMetaMap.get(meta.getClass().getSimpleName()).getDeclaredClasses()[0].getDeclaredConstructors()[1].newInstance(this);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Builder(this);
    }

    public static ReactorItem of(ItemStack itemStack) {
        return of(itemStack, true);
    }

    protected static ReactorItem of(ItemStack itemStack, boolean convert) {
        if (itemStack == null) { return null; }
        if (itemStack.getItemMeta() != null && craftMetaMap.containsKey(itemStack.getItemMeta().getClass().getSimpleName())) {
            try {
                if (convert) {
                    return (ReactorItem) craftMetaMap.get(itemStack.getItemMeta().getClass().getSimpleName()).getDeclaredMethod("of", ItemStack.class).invoke(null, itemStack);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Builder builder = ReactorItem.builder(itemStack.getType());
        builder.amount(itemStack.getAmount());
        builder.durability(itemStack.getDurability());
        if (itemStack.getItemMeta() != null) {
            builder.displayName(itemStack.getItemMeta().displayName());
            builder.lore(itemStack.getItemMeta().getLore());
            builder.enchants(itemStack.getItemMeta().getEnchants());
            builder.unbreakable(itemStack.getItemMeta().isUnbreakable());
        }
        return builder.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Material type) {
        Builder builder = builder();
        try {
            ItemMeta meta = Bukkit.getItemFactory().getItemMeta(type);
            if (craftMetaMap.containsKey(meta.getClass().getSimpleName())) {
                builder = (Builder) craftMetaMap.get(meta.getClass().getSimpleName()).getDeclaredMethod("builder").invoke(null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return builder.type(type);
    }

    public static class Builder<I extends ReactorItem, B extends Builder> extends AbstractBuilder<I, B> {

        protected Builder() {
        }

        protected Builder(I item) {
            super(item);
        }

        @Override
        protected I init() {
            return (I) new ReactorItem();
        }

        public I build() {
            return this.item;
        }

        public ItemStack buildStack() {
            return build().toItemStack();
        }

        public B type(Material type) {
            item.setType(type);
            return (B) this;
        }

        public B amount(int amount) {
            item.setAmount(amount);
            return (B) this;
        }

        public B durability(short durability) {
            item.setDurability(durability);
            return (B) this;
        }

        public B displayName(Component displayName) {
            item.setDisplayName(displayName);
            return (B) this;
        }

        public B lore(Collection<Component> lore) {
            item.getLore().addAll(lore);
            return (B) this;
        }

        public B lore(Component... lines) {
            item.getLore().addAll(Arrays.asList(lines));
            return (B) this;
        }

        public B lore(Component line) {
            item.getLore().add(line);
            return (B) this;
        }

        public B enchants(Map<Enchantment, Integer> enchants) {
            item.getEnchants().putAll(enchants);
            return (B) this;
        }

        public B enchant(Enchantment enchantment, int level) {
            item.getEnchants().put(enchantment, level);
            return (B) this;
        }

        public B flags(ItemFlag... flags) {
            item.getFlags().addAll(Arrays.asList(flags));
            return (B) this;
        }

        public B flag(ItemFlag flag) {
            item.getFlags().add(flag);
            return (B) this;
        }

        public B unbreakable(boolean unbreakable) {
            item.setUnbreakable(unbreakable);
            return (B) this;
        }

    }

}
