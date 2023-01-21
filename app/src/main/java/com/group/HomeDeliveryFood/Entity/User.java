package com.group.HomeDeliveryFood.Entity;

public class User {
    private String password, email, name,phoneNo;

    public User() {
    }

    public User( String email, String password, String name, String phoneNo) {

        this.password = password;
        this.email = email;
        this.phoneNo = phoneNo;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
