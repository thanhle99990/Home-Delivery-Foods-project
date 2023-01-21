package com.group.HomeDeliveryFood.Entity;

import java.util.Date;

public class Comment {
    String idRestaurant;
    String nameUser;
    String imageUrlUser;
    String review;
    Date timeCmt;
    String status;
    String idUser;
    Float ratingAmount;

    public Comment(String idRestaurant,String idUser ,String nameUser, String imageUrlUser, Float ratingAmount,String review,String status ,Date timeCmt) {
        this.idRestaurant = idRestaurant;
        this.idUser=idUser;
        this.nameUser = nameUser;
        this.imageUrlUser = imageUrlUser;
        this.ratingAmount=ratingAmount;
        this.review = review;
        this.status=status;
        this.timeCmt = timeCmt;
    }

    public Comment(String idRestaurant,String idUser , Float ratingAmount,String review,String status ,Date timeCmt) {
        this.idRestaurant = idRestaurant;
        this.idUser=idUser;
        this.ratingAmount=ratingAmount;
        this.review = review;
        this.status=status;
        this.timeCmt = timeCmt;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public float getRatingAmount() {
        return ratingAmount;
    }

    public void setRatingAmount(Float ratingAmount) {
        this.ratingAmount = ratingAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(String idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getImageUrlUser() {
        return imageUrlUser;
    }

    public void setImageUrlUser(String imageUrlUser) {
        this.imageUrlUser = imageUrlUser;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getTimeCmt() {
        return timeCmt;
    }

    public void setTimeCmt(Date timeCmt) {
        this.timeCmt = timeCmt;
    }
}
