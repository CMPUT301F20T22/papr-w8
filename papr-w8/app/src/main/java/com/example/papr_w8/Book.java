package com.example.papr_w8;


import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * This is a class that describes an instance of a Book object
 */
public class Book implements Serializable {
    private String title;
    private String author;
    private String ISBN;
    private String status;
    private String cover;
    private String owner;
    private double longitude;
    private double latitude;
    private String id;


    /**
     * Constructor to initialize a Book with 7 attributes
     * @param title
     *      This is a title string to be set
     * @param author
     *      This is an author string to be set
     * @param ISBN
     *      This is a 13-character book ISBN string to be set
     * @param status
     *      This is the current status of the book
     * @param cover
     *      This is the filename for the cover of the book
     * @param owner
     *      This is the owner of the book string to be set
     * @param id
     *      This is the book id in Firestore
     */
    public Book(String title, String author, String ISBN, String status, String cover, String owner, String id) {

        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.status = status;
        this.cover = cover;
        this.owner = owner;
        this.id = id;
    }

    /**
     * Constructor to initialize a Book with 6 attributes
     * @param title
     *      This is a title string to be set
     * @param author
     *      This is an author string to be set
     * @param ISBN
     *      This is a 13-character book ISBN string to be set
     * @param status
     *      This is the current status of the book
     * @param cover
     *      This is the filename for the cover of the book
     * @param owner
     *      This is the owner of the book string to be set
     */
    public Book(String title, String author, String ISBN, String status, String cover, String owner) {

        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.status = status;
        this.cover = cover;
        this.owner = owner;
    }

    /**
     * Constructor to initialize a Book with a cover provided
     * @param title
     *      This is a title string  to be set
     * @param author
     *      This is an author string to be set
     * @param ISBN
     *      This is a 13-character book ISBN string to be set
     * @param status
     *      This is the current status of a book to be set
     * @param cover
     *      This is a filename string to be set for retrieving an image from Firestore
     *
     */
    public Book(String title, String author, String ISBN, String status, String cover) {

        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.status = status;
        this.cover = cover;
    }

    /**
     * Constructor to initialize a Book without a cover provided
     * @param title
     *      This is a title string  to be set
     * @param author
     *      This is an author string to be set
     * @param ISBN
     *      This is a 13-character book ISBN string to be set
     * @param status
     *      This is the current status of a book to be set
     */
    public Book(String title, String author, String ISBN, String status) {

        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.status = status;
        this.cover = "default_book.png";
    }

    /**
     * This sets the Id of the book
     * @param Id
     *      The new Id of the book
     */
    public void setId( String Id ){ this.id = Id; }

    /**
     * This returns the Id of the book
     * @return
     *      Return string containing book id
     */
    public String getId(){ return this.id; }

    /**
     * This sets the owner of the book
     * @param owner
     *      The string containing the name of the owner
     */
    public void setOwner( String owner ){ this.owner = owner; }

    /**
     * This returns the owner of the book
     * @return
     *      Return the string containing the owner
     */
    public String getOwner(){ return this.owner; }

    /**
     * This returns the title of a Book
     * @return
     *      Return the title of a Book
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * This sets a new title of a Book
     * @param title
     *      The new title of a book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This returns the author of a Book
     * @return
     *      Return the author of a Book
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * This sets a new author of a Book
     * @param author
     *      The new author of a book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * This returns the ISBN of a Book
     * @return
     *      Return the ISBN of a Book
     */
    public String getISBN() {
        return this.ISBN;
    }

    /**
     * This sets a new ISBN of a Book
     * @param ISBN
     *      The new ISBN of a Book
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * This returns the status of a Book
     * @return
     *      Return the status of a Book
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * This sets the new status of a Book
     * @param status
     *      The new status of a Book
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This returns the cover of a Book
     * @return
     *      Return the cover of a Book
     */
    public String getCover() {
        return this.cover;
    }

    /**
     * This sets the new cover of a Book
     * @param cover
     *      The new cover of a Book
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * This returns the location of a Book
     * @return
     *      Return the location of a Book
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * This sets the new location of a Book
     * @param latitude
     *      The new location of a Book
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}