package com.thoughtworks.hp.models;

public class ShoppingList {

    public static final String SHOPPING_LIST_ID = "SHOPPING_LIST_ID";

    private long id;
    private String name;

    public ShoppingList(long id, String name) {
        this.id = id;
        this.name = ((name == null || name.trim().length() == 0) ? "New List" : name);
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
