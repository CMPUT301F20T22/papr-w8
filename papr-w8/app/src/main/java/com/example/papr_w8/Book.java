package com.example.papr_w8;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private String description;
    private String status;
    private String photo; // Using string as placeholder only

    private String location; // make Location class that has doubles for long/ Lat???

    public Book(String title, String author, String isbn, String description,String status, String photo, String location) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.status = status;
        this.photo = photo;//this must be made optional
        this.location = location;
    }
    //title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    //author
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    //isbn
    public String getISBN() {
        return isbn;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
    }
    //description
    public String getDescription() {
        return description;
    }

    public void setDescription(String isbn) {
        this.description = description;
    }
    //status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    //photo
    public String getPhoto() {
        return status;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    //location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
