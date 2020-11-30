package com.example.papr_w8;

import android.view.View;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.papr_w8.AddPack.AddBook;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests the AddBook activity functionality
 */
public class EditBookTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates a solo instance
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        //Login with a test acocunt
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.enterText((EditText) solo.getView((R.id.email)), "mazi@test.com");
        solo.enterText((EditText) solo.getView(R.id.password), "test123");
        solo.clickOnButton("Login");
        solo.waitForFragmentById(R.id.fragment);

        //go to books owned fragment
        solo.clickOnButton("Books Owned");
        //check for Text
        solo.getText("Books Owned", false);

        //add book to edit
        solo.clickOnButton("Add Book");
        View fab = getActivity().findViewById(R.id.myFloatingActionButton);
        solo.clickOnView(fab);
    }


//    /**
//     * Checks if edittexts being empty works before adding a book
//     */
//    @Test
//    public void checkEmpty() {
//        solo.assertCurrentActivity("Wrong Activity", EditBook.class);
//
//        solo.clickOnButton("Add Book");
//        assertTrue(solo.waitForText("Please enter title", 1, 2000));
//
//        solo.enterText((EditText) solo.getView(R.id.new_title_editText), "Test Title");
//        solo.clickOnButton("Add Book");
//        assertTrue(solo.waitForText("Please enter author's name", 1, 2000));
//
//
//        solo.enterText((EditText) solo.getView(R.id.new_author_editText), "Test Author");
//        solo.clickOnButton("Add Book");
//        assertTrue(solo.waitForText("Please enter valid ISBN", 1, 2000));
//    }

    /**
     * Checks if books are added when 'Add Book' is clicked
     */
    @Test
    public void checkEdit() {

        solo.assertCurrentActivity("Wrong Activity", AddBook.class);

        solo.enterText((EditText) solo.getView(R.id.new_title_editText), "Test Add Title");
        solo.enterText((EditText) solo.getView(R.id.new_author_editText), "Test Add Author");
        solo.enterText((EditText) solo.getView(R.id.new_isbn_editText), "TEST12345678");

        solo.clickOnButton("Add Book");
        assertTrue(solo.waitForText("Book Added", 1, 10000));

        solo.assertCurrentActivity("Wrong Activity", Host.class);

        solo.clickOnButton("Books Owned");
        assertTrue(solo.waitForText("Test Add Title", 1, 5000));
        assertTrue(solo.waitForText("Test Add Author", 1, 5000));
        assertTrue(solo.waitForText("TEST12345678", 1, 5000));




//        solo.assertCurrentActivity("Wrong Activity", AddBook.class);
//        solo.clickOnButton("Cancel");
//
        solo.clickOnButton("Books Owned");
        solo.clickInList(0);


//        solo.clickLongOnText("Edit Test");
//        assertTrue(solo.waitForText("Edit Test", 1, 2000));
//        assertTrue(solo.waitForText("Author Edit", 1, 2000));
//        assertTrue(solo.waitForText("TEST12345678", 1, 2000));
        solo.clickOnButton("Edit Description");

        solo.enterText((EditText) solo.getView(R.id.edit_title_editText), "Edited Title Test");
        solo.enterText((EditText) solo.getView(R.id.edit_author_editText), "Edited Author Test");
        solo.enterText((EditText) solo.getView(R.id.edit_isbn_editText), "EDITED12345678");
        solo.clickOnButton("Confirm");
        assertTrue(solo.waitForText("Book Edited", 1, 10000));

        solo.assertCurrentActivity("Wrong Activity", Host.class);

        solo.clickOnButton("Books Owned");
        assertTrue(solo.waitForText("Edited Title Test", 1, 2000));
        assertTrue(solo.waitForText("Edited Author Test", 1, 2000));
        assertTrue(solo.waitForText("EDITED12345678", 1, 2000));

    }

//    /**
//     * Check if File Chooser opens
//     */
//    @Test
//    public void checkFileChooser() {
//        solo.assertCurrentActivity("Wrong Activity", AddBook.class);
//        ImageButton imageButton = (ImageButton) solo.getView("imageButton");
//        solo.clickOnView(imageButton);
//        solo.assertCurrentActivity("Wrong Activity", AddBookCoverActivity.class);
//    }

    /**
     * Closes activites after tests
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
