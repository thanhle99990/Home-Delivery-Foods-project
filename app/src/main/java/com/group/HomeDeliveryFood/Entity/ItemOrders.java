package com.group.HomeDeliveryFood.Entity;

public class ItemOrders {
    String idItem, nameItem;
    Long quantityItem;
    Long totalAmount;
    String orderId;
    public ItemOrders(String idItem, Long quantityItem ) {
        this.idItem = idItem;
        this.quantityItem = quantityItem;
    }

    public ItemOrders(String idItem,String nameItem, String orderId,Long quantityItem, Long totalAmount) {
        this.idItem = idItem;
        this.nameItem = nameItem;
        this.orderId = orderId;
        this.quantityItem = quantityItem;
        this.totalAmount = totalAmount;
    }

    public ItemOrders(String idItem, String nameItem, Long quantityItem, Long totalAmount) {
        this.idItem = idItem;
        this.nameItem = nameItem;
        this.quantityItem = quantityItem;
        this.totalAmount = totalAmount;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public Long getQuantityItem() {
        return quantityItem;
    }

    public void setQuantityItem(Long quantityItem) {
        this.quantityItem = quantityItem;
    }
}
