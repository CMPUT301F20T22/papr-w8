package com.example.papr_w8;

import com.google.android.gms.maps.model.LatLng;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private String defaultTitle = "Harry Potter";
    private String defaultAuthor = "J K Rowling";
    private String defaultISBN = "12345";
    private String defaultStatus = "Available";
    private String defaultCover = "images/1606693874728.jpg";
    private String defaultOwner = "mazi@mail.com";
    private String defaultID = "defaultID";
    private LatLng defaultLocation = new LatLng(111.111, 123.123);

    private Book mockBook(){
        Book mockBook = new Book(defaultTitle, defaultAuthor, defaultISBN, defaultStatus, defaultCover, defaultOwner, defaultID);
        return mockBook;
    }

    @Test
    void testSetID(){
        // default data
        Book mockBook = mockBook();
        String sampleID = "SampleID";

        // set action
        mockBook.setId(sampleID);

        // assert
        assertEquals(sampleID, mockBook.getId());
    }

    @Test
    void testGetID(){
        Book mockBook = mockBook();
        assertEquals(defaultID,mockBook.getId());
    }

    @Test
    void testSetOwner(){
        Book mockBook = mockBook();
        String author = "sampleAuthor";
        mockBook.setAuthor(author);
        assertEquals(author, mockBook.getAuthor());
    }

    @Test
    void testGetOwner(){
        Book mockBook = mockBook();
        assertEquals(defaultOwner,mockBook.getOwner());
    }

    @Test
    void testSetTitle(){
        Book mockBook = mockBook();
        String title = "Peak";
        mockBook.setTitle(title);
        assertEquals(title, mockBook.getTitle());
    }

    @Test
    void testGetTitle(){
        Book mockBook = mockBook();
        assertEquals(defaultTitle,mockBook.getTitle());
    }

    @Test
    void testSetAuthor(){
        Book mockBook = mockBook();
        String author = "David Wang";
        mockBook.setAuthor(author);
        assertEquals(author, mockBook.getAuthor());
    }

    @Test
    void testGetAuthor(){
        Book mockBook = mockBook();
        assertEquals(defaultAuthor,mockBook.getAuthor());
    }

    @Test
    void testSetISBN(){
        Book mockBook = mockBook();
        String isbn = "129840948230974082793023";
        mockBook.setISBN(isbn);
        assertEquals(isbn, mockBook.getISBN());
    }

    @Test
    void testGetISBN(){
        Book mockBook = mockBook();
        assertEquals(defaultISBN,mockBook.getISBN());
    }

    @Test
    void testSetStatus(){
        Book mockBook = mockBook();
        String status = "Awaiting Approval";
        mockBook.setStatus(status);
        assertEquals(status, mockBook.getStatus());
    }

    @Test
    void testGetStatus(){
        Book mockBook = mockBook();
        assertEquals(defaultStatus,mockBook.getStatus());
    }

    @Test
    void testSetCover(){
        Book mockBook = mockBook();
        String cover = "images/default_book.png";
        mockBook.setCover(cover);
        assertEquals(cover, mockBook.getCover());
    }

    @Test
    void testGetCover(){
        Book mockBook = mockBook();
        assertEquals(defaultCover,mockBook.getCover());
    }

    @Test
    void testGetLocation(){
        Book mockBook = mockBook();
        mockBook.setLocation(defaultLocation);
        assertEquals(defaultLocation, mockBook.getLocation());
    }
}
