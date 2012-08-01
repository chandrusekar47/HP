package com.thoughtworks.hp.models;

public class Product {

    private long id;
    private String name;
    private String barcodeId;
    private String category;
    private boolean status;
    private double price;
    private String uom;
    private static final String INDIAN_CURRENCY_SYMBOL = "Rs. ";

    public Product(long productId, String name, String barcodeId, String category, boolean status, double price, String uom) {
        this.id = productId;
        this.name = name;
        this.barcodeId = barcodeId;
        this.category = category;
        this.status = status;
        this.price = price;
        this.uom = uom;
    }

    public String getName() {
        return this.name;
    }

    public String getCostAsString() {
        return INDIAN_CURRENCY_SYMBOL + Double.toString(this.price);
    }

    public double getPrice() {
        return price;
    }

    public String getUom() {
        return this.uom;
    }

    @Override
    public String toString(){
        return name;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(other == null || !(other instanceof Product)) return false;

        Product otherProduct = (Product) other;
        if(this.id == otherProduct.id) return true;
        if(this.name.equals(otherProduct.name) &&
           this.category.equals(otherProduct.category) &&
           this.barcodeId.equals(otherProduct.barcodeId) &&
           this.uom.equals(otherProduct.uom)) return true;

        return false;
    }
}


