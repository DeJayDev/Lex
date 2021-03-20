package dev.dejay.reactor.items.types;

import dev.dejay.reactor.items.ReactorItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;

@Data
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FireworkEffectItem extends ReactorItem {

    private FireworkEffect effect;

    @Override
    public ItemStack toItemStack() {
        ItemStack stack = super.toItemStack();
        if (stack.getItemMeta() != null) {
            FireworkEffectMeta meta = (FireworkEffectMeta) stack.getItemMeta();
            meta.setEffect(this.effect);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public static FireworkEffectItem of(ItemStack itemStack) {
        FireworkEffectItem item = (FireworkEffectItem) ReactorItem.of(itemStack, false);
        if (itemStack.getItemMeta() != null) {
            item.effect = ((FireworkEffectMeta) itemStack.getItemMeta()).getEffect();
        }
        return item;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends ReactorItem.Builder<FireworkEffectItem, Builder> {
        protected Builder() {
        }

        protected Builder(FireworkEffectItem item) {
            super(item);
        }

        @Override
        protected FireworkEffectItem init() {
            return new FireworkEffectItem();
        }

        @Override
        public FireworkEffectItem build() {
            return super.build();
        }

        public Builder effect(FireworkEffect effect) {
            item.effect = effect;
            return this;
        }

    }
}
