package com.group.HomeDeliveryFood.Entity;

import java.util.List;

public class RestaurantSection {
    String title;
    List<Food> categoryItemList;

    public RestaurantSection(String title, List<Food> categoryItemList) {
        this.title = title;
        this.categoryItemList = categoryItemList;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Food> getCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryItemList(List<Food> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }
}
