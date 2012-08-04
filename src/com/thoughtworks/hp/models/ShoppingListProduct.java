package com.thoughtworks.hp.models;

import com.thoughtworks.hp.datastore.ShoppingListProductTable;

public class ShoppingListProduct {

    private long shoppingListId;
    private long productId;
    private int quantity;
    private int fulfilled;

    private Product product;

    private static ShoppingListProductTable shoppingListProductTable;

    static {
        shoppingListProductTable = new ShoppingListProductTable();
    }

    public ShoppingListProduct(long productId, long shoppingListId) {
        this.productId = productId;
        this.shoppingListId = shoppingListId;
        this.quantity = 1;
    }

    public ShoppingListProduct(long productId, long shoppingListId, int quantity) {
        this(productId, shoppingListId);
        this.quantity = quantity;
    }

    public ShoppingListProduct(Product product, long shoppingListId) {
        this(product.getId(), shoppingListId);
        this.product = product;
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

    public double getUnitCost() {
        if(product == null) product = product();
        return product.getPrice();
    }

    public Product product() {
        if(product != null) return product;
        return Product.findById(productId);
    }

    public void productFulfilled(int fulfilled) {
        this.fulfilled = fulfilled;
    }

    public double totalCost() {
        if(product == null) this.product = product();
        return quantity * product.getPrice();
    }

    public static ShoppingListProduct create(ShoppingListProduct newShoppingListProduct) {
        return shoppingListProductTable.create(newShoppingListProduct);
    }

    public static double findQuantityOnProductAndShoppingList(long shoppingListId, long productId) {
        return shoppingListProductTable.findQuantityOnProductAndShoppingList(shoppingListId, productId);
    }

    public String getName() {
        if(product == null) this.product = product();
        return product.getName();
    }
}
