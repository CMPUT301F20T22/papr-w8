package com.example.papr_w8;

/**
 * Notification class
 */

public class Notification {
    private String senderId;
    private String senderName;
    private String type;
    private String bookTitle;
    private String viewed;
    private String book_id;

    public Notification(String senderId, String type, String bookTitle, String senderName, String book_id) {
        this.senderId = senderId;
        this.type = type;
        this.bookTitle = bookTitle;
        this.senderName = senderName;
        this.book_id = book_id;
        this.viewed = "false";
    }


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getSenderName() { return senderName; }

    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }
}
