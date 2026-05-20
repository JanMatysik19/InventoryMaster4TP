package com.example.inventorymaster.DataModels;

public class Item {
    private int id;
    private String sku;
    private String unit;
    private int categoryId;
    private String featuresCode;

    public Item(int id, String sku, String unit, int categoryId, String featuresCode) {
        this.id = id;
        this.sku = sku;
        this.unit = unit;
        this.categoryId = categoryId;
        this.featuresCode = featuresCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getFeaturesCode() {
        return featuresCode;
    }

    public void setFeaturesCode(String featuresCode) {
        this.featuresCode = featuresCode;
    }
}
