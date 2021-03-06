package com.lechampalamaison.loc.lechampalamaison.Model;

import java.util.Objects;

public class CartItem {
    private Item item;
    private int quantity;

    public CartItem() {
        // Empty constructor
    }

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem item1 = (CartItem) o;
        return quantity == item1.quantity &&
                Objects.equals(item, item1.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, quantity);
    }
}
