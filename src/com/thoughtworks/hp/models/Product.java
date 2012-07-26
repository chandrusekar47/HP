package com.thoughtworks.hp.models;

public class Product {

    private long id;
    private String name;
    private String barcodeId;
    private String category;

    public Product(long productId, String name, String barcodeId, String category) {
        this.id = productId;
        this.name = name;
        this.barcodeId = barcodeId;
        this.category = category;
    }
}
