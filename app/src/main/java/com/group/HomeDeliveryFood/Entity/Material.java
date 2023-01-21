package com.group.HomeDeliveryFood.Entity;

public class Material {
    String id;
    String name;
    String supplier;
    Long unitPrice;
    String imageUrl;
    Long quantity;
    String requiredAmount;
    String unit;
    public Material(String id, String name, String supplier, Long unitPrice, String imageUrl, Long quantity, String unit) {
        this.id = id;
        this.name = name;
        this.supplier = supplier;
        this.unitPrice = unitPrice;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRequiredAmount() {
        return requiredAmount;
    }

    public void setRequiredAmount(String requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
