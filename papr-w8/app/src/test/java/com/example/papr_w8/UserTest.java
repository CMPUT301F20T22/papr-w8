package com.example.papr_w8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for User class 
 */
public class UserTest {

    private String name = "Michelle";
    private String password = "password";
    private String email = "mxwang@ualberta.ca";
    private String address = "123 Happy Monkey Street";
    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<Notification> notifications = new ArrayList<Notification>();

    private User mockUser(){

        // Populate books - String title, String author, String ISBN, String status, String cover
        Book book1 = new Book("Book 1", "Author 1", "111", "Available", "imgaes/1.png");
        Book book2 = new Book("Book 2", "Author 2", "222", "Available", "imgaes/2.png");
        // populate notifications - String senderId, String type, String bookTitle, String senderName, String book_id
        Notification notification1 = new Notification("senderID1","request","Title1","sender1","bookID1");
        Notification notification2 = new Notification("senderID2","request","Title2","sender2","bookID2");

        books.add(book1);
        books.add(book2);
        notifications.add(notification1);
        notifications.add(notification2);

        // create new user
        User mockUser = new User("", "", "", "", books, notifications);

        // "test" setters
        mockUser.setPassword(password);
        mockUser.setEmail(email);
        mockUser.setAddress(address);
        mockUser.setName(name);

        return mockUser;
    }

    @Test
    void testGetName(){
        User user = mockUser();
        assertEquals(name, user.getName());
    }

    @Test
    void testGetPassword(){
        User user = mockUser();
        assertEquals(password, user.getPassword());
    }

    @Test
    void testGetEmail(){
        User user = mockUser();
        assertEquals(email, user.getEmail());
    }

    @Test
    void testGetAddress(){
        User user = mockUser();
        assertEquals(address, user.getAddress());
    }

    @Test
    void testGetBooks(){
        // check getBooks() works
        User user = mockUser();
        assertEquals(books, user.getBooks());

        // check fetched arrayList contains the right books
        ArrayList booksList = user.getBooks();
        Book book1 = (Book) booksList.get(0);
        String book1Title = book1.getTitle();
        assertEquals("Book 1", book1Title);
    }

    @Test
    void testAddBooks(){
        // setup
        User user = mockUser();
        Book book3 = new Book("Book 3", "Author 3", "333", "Available", "imgaes/3.png");

        // do
        user.addBooks(book3);
        Book testBook3 = user.getBooks().get(2);
        String bookAuthor = testBook3.getAuthor();

        // test
        assertEquals(3,user.getBooks().size());
        assertEquals("Author 3",bookAuthor);
    }

    @Test
    void testGetNotifications(){
        User user = mockUser();
        assertEquals(notifications, user.getNotifications());

        ArrayList nList = user.getNotifications();
        Notification n1 = (Notification) nList.get(0);
        String n1ID = n1.getSenderId();
        assertEquals("senderID1", n1ID);
    }

}
