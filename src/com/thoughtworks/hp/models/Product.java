package com.thoughtworks.hp.models;

public class Product {

    private long id;
    private String name;
    private String barcodeId;
    private String category;
    private boolean status;
    private double cost;
    private String uom;

    public Product(long productId, String name, String barcodeId, String category, boolean status, double cost, String uom) {
        this.id = productId;
        this.name = name;
        this.barcodeId = barcodeId;
        this.category = category;
        this.status = status;
        this.cost = cost;
        this.uom = uom;
    }

    public String getName() {
        return this.name;
    }

    public String getCostAsString() {
        return Double.toString(this.cost);
    }

    public String getUom() {
        return this.uom;
    }

    @Override
    public String toString(){
        return name;
    }
}


