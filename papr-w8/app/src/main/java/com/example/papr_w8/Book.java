package com.example.papr_w8;


public class Book {
    private String title;
    private String author;
    private String ISBN;
    private String status;
    private String cover;

    public Book(String title, String author, String ISBN, String status, String cover) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.status = status;
        this.cover = cover;
    }

    public Book(String title, String author, String ISBN, String status) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.status = status;
        this.cover = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
