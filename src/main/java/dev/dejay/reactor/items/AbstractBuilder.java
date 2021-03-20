package dev.dejay.reactor.items;

import org.bukkit.inventory.ItemStack;

public abstract class AbstractBuilder<I extends AbstractItem, B extends AbstractBuilder> {

    protected final I item;

    protected AbstractBuilder() {
        this.item = init();
    }

    protected AbstractBuilder(I item) {
        this.item = item;
    }

    protected abstract I init();

    protected abstract I build();

    protected abstract ItemStack buildStack();

}
