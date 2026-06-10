package com.example.inventorymaster.Modules.Items;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String featuresCode;
    private String description;
    private String price;
    private int quantity;

    public Item(int id, String featuresCode, String description, String price, int quantity) {
        this.id = id;
        this.featuresCode = featuresCode;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeaturesCode() {
        return featuresCode;
    }

    public void setFeaturesCode(String featuresCode) {
        this.featuresCode = featuresCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
