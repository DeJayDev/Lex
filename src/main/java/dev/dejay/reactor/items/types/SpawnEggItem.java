package dev.dejay.reactor.items.types;

import dev.dejay.reactor.items.ReactorItem;
import dev.dejay.reactor.items.types.SkullItem.Builder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.material.SpawnEgg;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

@Data
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class SpawnEggItem extends ReactorItem {

    private EntityType entityType;

    @Override
    public ItemStack toItemStack() {
        ItemStack stack = super.toItemStack();
        if (stack.getItemMeta() != null) {
            SpawnEggMeta meta = (SpawnEggMeta) stack.getItemMeta();
            meta.setSpawnedType(this.entityType);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public static SpawnEggItem of(ItemStack itemStack) {
        SpawnEggItem item = (SpawnEggItem) ReactorItem.of(itemStack, false);
        if (itemStack.getItemMeta() != null) {
            item.entityType = ((SpawnEggMeta) itemStack.getItemMeta()).getSpawnedType();
        }
        return item;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends ReactorItem.Builder<SpawnEggItem, SpawnEggItem.Builder> {

        protected Builder() {
        }

        protected Builder(SpawnEggItem item) {
            super(item);
        }

        @Override
        protected SpawnEggItem init() {
            return new SpawnEggItem();
        }

        @Override
        public SpawnEggItem build() {
            return super.build();
        }

        public SpawnEggItem.Builder entityType(EntityType entityType) {
            item.entityType = entityType;
            return this;
        }

    }

}
