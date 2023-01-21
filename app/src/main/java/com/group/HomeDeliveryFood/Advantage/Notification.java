package com.group.HomeDeliveryFood.Advantage;

public class Notification {
    String idUser;
    String note;
    String time;
    String nameRestaurant;
    String noteID;

    public Notification(String noteID,String idUser,String nameRestaurant ,String note, String time) {
        this.noteID=noteID;
        this.nameRestaurant=nameRestaurant;
        this.idUser = idUser;
        this.note = note;
        this.time = time;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }
}
