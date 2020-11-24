package com.example.papr_w8;

/**
 * Notification class
 */

public class Notification {
    private String senderId;
    private String ISBN;
    private String type;
    private String senderName;
    private String bookTitle;
    private String viewed;

    public Notification(String senderId, String ISBN, String type, String bookTitle, String senderName) {
        this.senderId = senderId;
        this.ISBN = ISBN;
        this.type = type;
        this.bookTitle = bookTitle;
        this.senderName = senderName;
        this.viewed = "false";
    }


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }


    public String getViewed(){
        return this.viewed;
    }

    public void setViewed(){
        this.viewed = "true";
    }

}
