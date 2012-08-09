package com.thoughtworks.hp.presenters;

import com.thoughtworks.hp.models.Product;
import com.thoughtworks.hp.models.ShoppingList;
import com.thoughtworks.hp.models.ShoppingListProduct;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListPresenter {

    private String shoppingListName;
    private int totalDistinctItems;
    private double totalShoppingListCost;
    private int totalUnitCount;
    private List<ShoppingListProduct> shoppingListProducts = new ArrayList<ShoppingListProduct>();

    private ShoppingList shoppingList;

    public ShoppingListPresenter(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public List<ShoppingListProduct> shoppingListProducts() {
        return shoppingList.shoppingListProducts();
    }

    public String shoppingListName() {
        return shoppingList.getName();
    }

    public int totalDistinctProducts() {
        return shoppingList.totalDistinctProducts();
    }

    public int totalProductUnitCount() {
        return shoppingList.totalProductUnitCount();
    }

    public String totalShoppingListCost() {
        return Double.toString(shoppingList.totalCost());
    }

    public void addSelectedProductToShoppingList(Product product) {
        this.shoppingList.addNewProduct(product);
        this.shoppingListProducts = shoppingList.shoppingListProducts();
    }
}
