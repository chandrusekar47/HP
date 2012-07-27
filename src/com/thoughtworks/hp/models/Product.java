package com.thoughtworks.hp.models;

public class Product {

    private long id;
    private String name;
    private String barcodeId;
    private String category;
    private boolean status;

    public Product(long productId, String name, String barcodeId, String category, boolean status) {
        this.id = productId;
        this.name = name;
        this.barcodeId = barcodeId;
        this.category = category;
        this.status = status;
    }

    @Override
    public String toString(){
        return name;
    }
}
