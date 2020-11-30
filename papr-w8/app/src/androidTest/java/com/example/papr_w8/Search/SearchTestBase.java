package com.example.papr_w8.Search;

import android.os.Handler;
import static org.junit.Assert.assertTrue;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.papr_w8.Host;
import com.example.papr_w8.MainActivity;
import com.example.papr_w8.ProfilePack.EditProfile;
import com.example.papr_w8.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.robotium.solo.Solo;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SearchTestBase {
    private Solo solo;
    private String testTitle;
    private String testISBN;
    private String testAuthor;
    private String testStatus;
    private String testOwner;
    private String testBookCoverFile;
    private StorageReference storageReference;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    final FirebaseFirestore fbDB = FirebaseFirestore.getInstance();

    @Rule
    public ActivityTestRule<Host> rule = new ActivityTestRule<Host>(Host.class, true, true);


    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
//        //Add a testBook
//        testTitle = "Default Title";
//        testISBN = "Default ISBN";
//        testAuthor = "Default Author";
//        testStatus = "Available";
//        testOwner = "skavalin@ualberta.ca";
//        testBookCoverFile = "default_book.png";
//        final Map<String, Object> book = new HashMap<>();
//        book.put("Title", testTitle);
//        book.put("Author", testAuthor);
//        book.put("ISBN", testISBN);
//        book.put("Status", testStatus);
//        book.put("Book Cover", testBookCoverFile);
//        book.put("Owner", testOwner);
//
//        // Implementation for adding book details to the firestore collection
//        fbDB.collection("Users").document(testOwner).collection("Books Owned")
//                .add(book);
        //fbDB.collection("Books").add(book);
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", Host.class);
        solo.waitForFragmentById(R.id.fragment);
        View search = solo.getView("search");
        solo.clickOnImage(2);

    }


    /**
     * check if everything is presented correctly on profile page
     */
    @Test
    public void searchForAvailableBook() throws InterruptedException {
//        solo.waitForFragmentById(R.id.search);
        //search for "A Clockwork Orange"
        solo.enterText((EditText) solo.getView((R.id.search_edit_text)), "Clock");
        assertTrue(solo.waitForText("Clock",1,2000));
        // Click on result list
        solo.clickOnView(solo.getView(R.id.result_list));
        //click on book
        solo.clickOnText("A Clockwork Orange");
        //Assert that the book is available
        assertTrue(solo.waitForText("Available",1,2000));
        assertTrue(solo.waitForText("A Clockwork Orange",1,2000));
        //view the book
        solo.waitForFragmentById(R.id.search);

    }
    @Test
    public void searchForUser() throws InterruptedException{
        //click on dropDown menu
        solo.clickOnText("Ava. Books");
        //select the "Users option
        solo.clickOnText("Users");
        //Assert user search
        assertTrue(solo.waitForText("Users",1,2000));
        //search for Scotta
        solo.enterText((EditText) solo.getView((R.id.search_edit_text)), "Scotta");
        assertTrue(solo.waitForText("scott_test_acc@test.com",1,2000));
        //click on the user
        solo.clickOnText("scott_test_acc@test.com");
        // assert that it is the correct user
        assertTrue(solo.waitForText("Scotta",1,2000));
        //assert that the user profile page has been navigated to
        assertTrue(solo.waitForText("User Profile",1,2000));
        solo.waitForFragmentById(R.id.search);
    }

    @Test
    public void searchForAllBooks() throws InterruptedException{
        //click on dropDown menu
        solo.clickOnText("Ava. Books");
        //select the All books option
        solo.clickOnText("All Books");
        //Assert all book status can be seen

        if (solo.waitForText("Available",1,2000)){
                assertTrue(solo.waitForText("Available", 1, 2000));
                //click on the Available book
                solo.clickOnText("Available");
                //verify that we are on the Description page
                assertTrue(solo.waitForText("Description",1,2000));
                //assert the book is available
                solo.waitForText("Available", 1, 2000);
        }


            solo.waitForFragmentById(R.id.search);

        if ( solo.waitForText("Requested" ,1,2000)){
            assertTrue(solo.waitForText("Requested" ,1,2000));
            //click on the Available book
            solo.clickOnText("Requested");
            //verify that we are on the Description page
            assertTrue(solo.waitForText("Description",1,2000));
            //assert the book is Requested
            solo.waitForText("Requested", 1, 2000);
        }
        if ( solo.waitForText("Awaiting Approval" ,1,2000)){
                assertTrue(solo.waitForText("Awaiting Approval" ,1,2000));
            //click on the Available book
            solo.clickOnText("Awaiting Approval");
            //verify that we are on the Description page
            assertTrue(solo.waitForText("Description",1,2000));
            //assert the book is Awaiting Approval
            solo.waitForText("Awaiting Approval", 1, 2000);
            }

    }


    /**
     * Close activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
//        fbDB.collection("Users").document(testOwner).collection("Books Owned")
//                .document(book.getId()).delete();

        solo.finishOpenedActivities();
    }

}