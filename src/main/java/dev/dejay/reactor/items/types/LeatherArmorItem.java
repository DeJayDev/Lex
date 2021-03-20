package dev.dejay.reactor.items.types;

import dev.dejay.reactor.items.ReactorItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

@Data
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class LeatherArmorItem extends ReactorItem {

    private Color color;

    @Override
    public ItemStack toItemStack() {
        ItemStack stack = super.toItemStack();
        if (stack.getItemMeta() != null) {
            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            meta.setColor(this.color);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public static LeatherArmorItem of(ItemStack itemStack) {
        LeatherArmorItem item = (LeatherArmorItem) ReactorItem.of(itemStack, false);
        if (itemStack.getItemMeta() != null) {
            item.color = ((LeatherArmorMeta) itemStack.getItemMeta()).getColor();
        }
        return item;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends ReactorItem.Builder<LeatherArmorItem, Builder> {
        protected Builder() {
        }

        protected Builder(LeatherArmorItem item) {
            super(item);
        }

        @Override
        protected LeatherArmorItem init() {
            return new LeatherArmorItem();
        }

        @Override
        public LeatherArmorItem build() {
            return super.build();
        }

        public Builder color(Color color) {
            item.color = color;
            return this;
        }

    }

}
