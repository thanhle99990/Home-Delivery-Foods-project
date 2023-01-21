package com.group.HomeDeliveryFood.Entity;

import java.util.List;

public class Category {
    String name;
    List<Restaurant> categoryItemList;

    public Category(String categoryTitle, List<Restaurant> categoryItemList) {
        this.name = categoryTitle;
        this.categoryItemList = categoryItemList;
    }


    public Category(String categoryTitle) {
        this.name = categoryTitle;
    }

    public List<Restaurant> getCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryItemList(List<Restaurant> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }

    public String getCategoryTitle() {
        return name;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.name = categoryTitle;
    }


}
