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
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

@Data
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class BookItem extends ReactorItem {

    private String title;
    private String author;
    private List<Component> pages = new ArrayList<>();

    @Override
    public ItemStack toItemStack() {
        ItemStack stack = super.toItemStack();
        if (stack.getItemMeta() != null) {
            BookMeta meta = (BookMeta) stack.getItemMeta();
            meta.setTitle(title);
            meta.setAuthor(author);
            meta.pages().addAll(pages);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public static BookItem of(ItemStack itemStack) {
        BookItem item = (BookItem) ReactorItem.of(itemStack, false);
        if (itemStack.getItemMeta() != null) {
            item.title = ((BookMeta) itemStack.getItemMeta()).getTitle();
            item.author = ((BookMeta) itemStack.getItemMeta()).getAuthor();
            item.pages.addAll(((BookMeta) itemStack.getItemMeta()).pages());
        }
        return item;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends ReactorItem.Builder<BookItem, Builder> {

        protected Builder() {
        }

        protected Builder(BookItem item) {
            super(item);
        }

        @Override
        protected BookItem init() {
            return new BookItem();
        }

        @Override
        public BookItem build() {
            return super.build();
        }

        public Builder title(String title) {
            item.title = title;
            return this;
        }

        public Builder author(String author) {
            item.author = author;
            return this;
        }

        public Builder pages(Collection<Component> pages) {
            item.pages.addAll(pages);
            return this;
        }

        public Builder pages(Component... pages) {
            item.pages.addAll(Arrays.asList(pages));
            return this;
        }

        public Builder page(Component page) {
            item.pages.add(page);
            return this;
        }

    }

}
