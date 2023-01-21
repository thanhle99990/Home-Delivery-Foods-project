package com.group.HomeDeliveryFood.Entity;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private String customerId;
    private String addressCustomer;
    private String phoneCustomer;
    private String idRestaurant;
    private String idOrder;
    public ArrayList<ItemOrders> itemOrders;
    private Long totalBill;
    private Date date;
    private String customerName;
    private String orderStatus;

    public Order() {
    }

    public Order(String customerId, String nameuser, String addressuser, String phoneNumber, String restaurantId) {
        this.customerId = customerId;
        this.customerName = nameuser;
        this.addressCustomer = addressuser;
        this.phoneCustomer = phoneNumber;
        this.idRestaurant = restaurantId;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public Long getTotalBill() {
        return totalBill;
    }


    public void setTotalBill(Long totalBill) {
        this.totalBill = totalBill;
    }

    public Order(String idOrder, String customerId,String customerName, String addressCustomer, String idRestaurant, ArrayList<ItemOrders> itemOrders, Date date,String orderStatus) {
        this.customerId = customerId;
        this.addressCustomer = addressCustomer;
        this.phoneCustomer = phoneCustomer;
        this.idRestaurant = idRestaurant;
        this.idOrder = idOrder;
        this.itemOrders = itemOrders;
        this.date = date;
        this.orderStatus = orderStatus;
        this.customerName = customerName;
    }

    public Order(String idOrder, String customerId, String customerName, String phoneCustomer, String addressCustomer, String idRestaurant, Date date, Long totalBill) {
        this.customerId = customerId;
        this.addressCustomer = addressCustomer;
        this.phoneCustomer = phoneCustomer;
        this.idRestaurant = idRestaurant;
        this.idOrder = idOrder;
        this.totalBill = totalBill;
        this.date = date;
        this.customerName = customerName;
    }
    public Order(String customerId, String customerName,  String phoneCustomer, String addressCustomer, String idRestaurant, Date date, Long totalBill) {
        this.customerId = customerId;
        this.addressCustomer = addressCustomer;
        this.phoneCustomer = phoneCustomer;
        this.phoneCustomer = phoneCustomer;
        this.idRestaurant = idRestaurant;
        this.idOrder = idOrder;
        this.totalBill = totalBill;
        this.date = date;
        this.customerName = customerName;
    }

    public Order(String idOrder, String customerId, String customerName, String phoneCustomer,  String addressCustomer, String idRestaurant, ArrayList<ItemOrders> itemOrders, Date date, Long totalBill, String orderStatus) {
        this.idOrder=idOrder;
        this.customerId = customerId;
        this.phoneCustomer = phoneCustomer;
        this.addressCustomer = addressCustomer;
        this.idRestaurant = idRestaurant;
        this.itemOrders = itemOrders;
        this.date = date;
        this.totalBill=totalBill;
        this.orderStatus = orderStatus;
        this.customerName = customerName;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAddressCustomer() {
        return addressCustomer;
    }

    public void setAddressCustomer(String addressCustomer) {
        this.addressCustomer = addressCustomer;
    }

    public String getPhoneCustomer() {
        return phoneCustomer;
    }

    public void setPhoneCustomer(String phoneCustomer) {
        this.phoneCustomer = phoneCustomer;
    }

    public String getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(String idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public ArrayList<ItemOrders> getItemOrders() {
        return itemOrders;
    }

    public void setItemOrders(ArrayList<ItemOrders> itemOrders) {
        this.itemOrders = itemOrders;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void addItemOrders(ArrayList<ItemOrders> tempItemOrders) {
    }
}
