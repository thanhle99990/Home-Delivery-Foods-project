package com.group.HomeDeliveryFood.Entity;

import java.util.List;

public class AllFood {
    String categoryId;
    List<Food> categoryItemList;

    public AllFood(String categoryId, List<Food> categoryItemList) {
        this.categoryId = categoryId;
        this.categoryItemList = categoryItemList;
    }

    public List<Food> getCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryItemList(List<Food> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
