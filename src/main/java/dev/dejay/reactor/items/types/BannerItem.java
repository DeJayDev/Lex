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
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

@Data
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class BannerItem extends ReactorItem {

    private List<Pattern> patterns = new ArrayList<>();

    @Override
    public ItemStack toItemStack() {
        ItemStack stack = super.toItemStack();
        if(stack.getItemMeta() != null) {
            BannerMeta meta = (BannerMeta) stack.getItemMeta();
            meta.setPatterns(patterns);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public static BannerItem of(ItemStack itemStack) {
        BannerItem item = (BannerItem) ReactorItem.of(itemStack, false);
        if(itemStack.getItemMeta() != null) {
            ((BannerMeta) itemStack.getItemMeta()).getPatterns().addAll(item.patterns);
        }
        return item;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends ReactorItem.Builder<BannerItem, Builder> {
        protected Builder() {
        }

        protected Builder(BannerItem item) {
            super(item);
        }

        @Override
        protected BannerItem init() {
            return new BannerItem();
        }

        @Override
        public BannerItem build() {
            return super.build();
        }

        public Builder patterns(Collection<Pattern> patterns) {
            item.patterns.addAll(patterns);
            return this;
        }

        public Builder patterns(Pattern... patterns) {
            item.patterns.addAll(Arrays.asList(patterns));
            return this;
        }

        public Builder pattern(Pattern pattern) {
            item.patterns.add(pattern);
            return this;
        }

    }

}
