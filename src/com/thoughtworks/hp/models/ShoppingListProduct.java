package com.thoughtworks.hp.models;

public class ShoppingListProduct {

    private long shoppingListId;
    private long productId;
    private int quantity;

    public ShoppingListProduct(long productId, long shoppingListId) {
        this.productId = productId;
        this.shoppingListId = shoppingListId;
        this.quantity = 1;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getShoppingListId() {
        return shoppingListId;
    }

    public long getProductId() {
        return productId;
    }

}
