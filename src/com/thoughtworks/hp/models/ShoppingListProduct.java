package com.thoughtworks.hp.models;

public class ShoppingListProduct {

    private long shoppingListId;
    private long productId;

    public ShoppingListProduct(long productId, long shoppingListId) {
        this.productId = productId;
        this.shoppingListId = shoppingListId;
    }

    public long getShoppingListId() {
        return shoppingListId;
    }

    public long getProductId() {
        return productId;
    }
}
