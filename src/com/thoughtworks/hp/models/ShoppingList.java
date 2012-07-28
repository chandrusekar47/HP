package com.thoughtworks.hp.models;

import java.util.List;

public class ShoppingList {

    private long id;
    private String name;
    private List<Product> products;

    public ShoppingList(long id, String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }
}
