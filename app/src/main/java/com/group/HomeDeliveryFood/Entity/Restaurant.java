package com.group.HomeDeliveryFood.Entity;

import com.group.HomeDeliveryFood.Cart.FoodCart;

public class Restaurant  {
    private String restaurantId;
    private String image;
    private String name;
    private String address;
    private String categoryId;
    public FoodCart foodCart;
    private String startTime;
    private String endTime;
    private float ratingStar;
    private int ratingCounts;

    public Restaurant(String restaurantId,String name, String image, String address, String categoryId) {
        this.restaurantId = restaurantId;
        this.image = image;
        this.name = name;
        this.address = address;
        this.categoryId = categoryId;
        foodCart=new FoodCart();
    }

    public Restaurant(String restaurantId,String name, String image, String address) {
        this.restaurantId=restaurantId;
        this.image = image;
        this.name = name;
        this.address = address;
        foodCart=new FoodCart();
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public FoodCart getFoodCart() {
        return foodCart;
    }

    public void setFoodCart(FoodCart foodCart) {
        this.foodCart = foodCart;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public float getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(float ratingStar) {
        this.ratingStar = ratingStar;
    }

    public int getRatingCounts() {
        return ratingCounts;
    }

    public void setRatingCounts(int ratingCounts) {
        this.ratingCounts = ratingCounts;
    }
}
