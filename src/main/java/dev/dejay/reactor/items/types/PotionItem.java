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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

@Data
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class PotionItem extends ReactorItem {

    private PotionData basePotion;
    private List<PotionEffect> customEffects = new ArrayList<>();

    @Override
    public ItemStack toItemStack() {
        ItemStack stack = super.toItemStack();
        if (stack.getItemMeta() != null) {
            PotionMeta meta = (PotionMeta) stack.getItemMeta();
            meta.setBasePotionData(this.basePotion);
            for (PotionEffect effect : this.customEffects) {
                meta.addCustomEffect(effect, true);
            }
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public static PotionItem of(ItemStack itemStack) {
        PotionItem item = (PotionItem) ReactorItem.of(itemStack, false);
        if (itemStack.getItemMeta() != null) {
            item.basePotion = ((PotionMeta) itemStack.getItemMeta()).getBasePotionData();
            item.customEffects.addAll(((PotionMeta) itemStack.getItemMeta()).getCustomEffects());
        }
        return item;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends ReactorItem.Builder<PotionItem, Builder> {
        protected Builder() {
        }

        protected Builder(PotionItem item) {
            super(item);
        }

        @Override
        protected PotionItem init() {
            return new PotionItem();
        }

        @Override
        public PotionItem build() {
            return super.build();
        }

        public Builder basePotion(PotionData basePotion) {
            item.basePotion = basePotion;
            return this;
        }

        public Builder effects(Collection<PotionEffect> effects) {
            item.customEffects.addAll(effects);
            return this;
        }

        public Builder effects(PotionEffect... effects) {
            item.customEffects.addAll(Arrays.asList(effects));
            return this;
        }

        public Builder effect(PotionEffect effect) {
            item.customEffects.add(effect);
            return this;
        }

    }

}
