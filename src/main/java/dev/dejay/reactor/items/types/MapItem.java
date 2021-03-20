package dev.dejay.reactor.items.types;

import dev.dejay.reactor.items.ReactorItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

@Data
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class MapItem extends ReactorItem {

    private boolean scaling;

    @Override
    public ItemStack toItemStack() {
        ItemStack stack = super.toItemStack();
        if (stack.getItemMeta() != null) {
            MapMeta meta = (MapMeta) stack.getItemMeta();
            meta.setScaling(this.scaling);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public static MapItem of(ItemStack itemStack) {
        MapItem item = (MapItem) ReactorItem.of(itemStack, false);
        if (itemStack.getItemMeta() != null) {
            item.scaling = ((MapMeta) itemStack.getItemMeta()).isScaling();
        }
        return item;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends ReactorItem.Builder<MapItem, Builder> {
        protected Builder() {
        }

        protected Builder(MapItem item) {
            super(item);
        }

        @Override
        protected MapItem init() {
            return new MapItem();
        }

        @Override
        public MapItem build() {
            return super.build();
        }

        public Builder scaling(boolean scaling) {
            item.scaling = scaling;
            return this;
        }

    }


}
