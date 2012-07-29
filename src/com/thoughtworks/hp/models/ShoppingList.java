package com.thoughtworks.hp.models;

import java.util.List;

public class ShoppingList {

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
}
