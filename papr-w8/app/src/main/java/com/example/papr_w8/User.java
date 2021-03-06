package com.example.papr_w8;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String name;
    private String password;
    private String email;
    private String address;
    private ArrayList<Book> books;
    private ArrayList<Notification> notifications;

    public User(String name, String password, String email, String address, ArrayList<Book> books, ArrayList<Notification> notifications){
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.books = books;
        this.notifications = notifications;
    }

    public User(String name, String password, String email, String address){
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public User(){
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void addBooks(Book book) {
        books.add(book);
    }

    public ArrayList<Notification> getNotifications() { return notifications; }


}
