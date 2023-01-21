package com.group.HomeDeliveryFood.Entity;

public class Food {
    private String foodId;
    private String name;
    private Long unitPrice;
    private String category;
    private String image;
    private String description;
    private Restaurant restaurant;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Food(String foodId, String name, Long unitPrice, String category, String image,String description){
        this.foodId = foodId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.category = category;
        this.image = image;
        this.description=description;
    }

    public Food(String foodId,String name, Long unitPrice, String category, String image, Restaurant restaurant) {
        this.foodId = foodId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.category = category;
        this.image = image;
        this.restaurant = restaurant;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
