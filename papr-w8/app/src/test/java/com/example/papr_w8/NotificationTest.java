package com.example.papr_w8;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NotificationTest {

    private String senderId = "testSenderID";
    private String type = "request";
    private String senderName = "mazi@mail.com";
    private String bookTitle = "Grey's Anatomy";
    private String book_id = "bookID123";
    private String notification_id = "notification 123";

    private Notification mockNotification(){
        Notification notification = new Notification("", "", "","", "");
        notification.setSenderId(senderId);
        notification.setType(type);
        notification.setSenderName(senderName);
        notification.setBookTitle(bookTitle);
        notification.setBook_id(book_id);
        notification.setViewed(); // sets viewed = true
        notification.setNotification_id(notification_id);
        return notification;
    }

    @Test
    void testGetSenderId(){
        Notification notification = mockNotification();
        assertEquals(senderId, notification.getSenderId());
    }

    @Test
    void testGetType(){
        Notification notification = mockNotification();
        assertEquals(type, notification.getType());
    }

    @Test
    void testGetBookTitle(){
        Notification notification = mockNotification();
        assertEquals(bookTitle, notification.getBookTitle());
    }

    @Test
    void testGetSenderName(){
        Notification notification = mockNotification();
        assertEquals(senderName, notification.getSenderName());
    }

    @Test
    void testGetBookId(){
        Notification notification = mockNotification();
        assertEquals(book_id, notification.getBook_id());
    }

    @Test
    void testNotificationID(){
        Notification notification = mockNotification();
        assertEquals(notification_id, notification.getNotification_id());
    }

    @Test
    void testViewed(){
        Notification notification = mockNotification();
        assertTrue(notification.getViewed());
    }
}
