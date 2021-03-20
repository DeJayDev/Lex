package dev.dejay.reactor.items.types;

import dev.dejay.reactor.items.ReactorItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

@Data
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FireworkItem extends ReactorItem {

    private List<FireworkEffect> effects = new ArrayList<>();
    private int power;

    @Override
    public ItemStack toItemStack() {
        ItemStack stack = super.toItemStack();
        if (stack.getItemMeta() != null) {
            FireworkMeta meta = (FireworkMeta) stack.getItemMeta();
            meta.addEffects(this.effects);
            meta.setPower(this.power);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public static FireworkItem of(ItemStack itemStack) {
        FireworkItem item = (FireworkItem) ReactorItem.of(itemStack, false);
        if (itemStack.getItemMeta() != null) {
            item.effects.addAll(((FireworkMeta) itemStack.getItemMeta()).getEffects());
            item.power = ((FireworkMeta) itemStack.getItemMeta()).getPower();
        }
        return item;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends ReactorItem.Builder<FireworkItem, Builder> {

        protected Builder() {
        }

        protected Builder(FireworkItem item) {
            super(item);
        }

        @Override
        protected FireworkItem init() {
            return new FireworkItem();
        }

        @Override
        public FireworkItem build() {
            return super.build();
        }

        public Builder effects(Collection<FireworkEffect> effects) {
            item.effects.addAll(effects);
            return this;
        }

        public Builder effects(FireworkEffect... effects) {
            item.effects.addAll(Arrays.asList(effects));
            return this;
        }

        public Builder effect(FireworkEffect effect) {
            item.effects.add(effect);
            return this;
        }

        public Builder power(int power) {
            item.power = power;
            return this;
        }

    }
}
