package com.example.papr_w8;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String name;
    private String password;
//    private String contact;
    private String email;
    private String address;
    private ArrayList<Books> books;

    public User(String name, String password, String email, String address){
        this.name = name;
        this.password = password;
//        this.contact = contact;
        this.email = email;
        this.address = address;
        this.books = new ArrayList<Books>();
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

    public ArrayList<Books> getBooks() {
        return books;
    }

    public void addBooks(Books book) {
        books.add(book);
    }

//    public String getContact() {
//        return contact;
//    }
//
//    public void setContact(String contact) {
//        this.contact = contact;
//    }
}
