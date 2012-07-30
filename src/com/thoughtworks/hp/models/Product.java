package com.thoughtworks.hp.models;

public class Product {

    private long id;
    private String name;
    private String barcodeId;
    private String category;
    private boolean status;
    private double cost;
    private String uom;
    private static final String INDIAN_CURRENCY_SYMBOL = "Rs. ";
    private String xPos;
    private String yPos;

    public Product(long productId, String name, String barcodeId, String category, boolean status, double cost, String uom, String xPos, String yPos) {
        this.id = productId;
        this.name = name;
        this.barcodeId = barcodeId;
        this.category = category;
        this.status = status;
        this.cost = cost;
        this.uom = uom;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public String getName() {
        return this.name;
    }

    public String getCostAsString() {
        return INDIAN_CURRENCY_SYMBOL + Double.toString(this.cost);
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
    
    public String getCordinates(){
    	return xPos + "," + yPos;
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


