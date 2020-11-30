package com.example.papr_w8;

import android.widget.EditText;
import android.widget.ImageButton;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.papr_w8.AddPack.AddBook;
import com.example.papr_w8.AddPack.AddBookCoverActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests the AddBook activity functionality
 */
public class AddBookTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<AddBook> rule =
            new ActivityTestRule<AddBook>(AddBook.class, true, true);

    /**
     * Runs before all tests and creates a solo instance
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Checks if edittexts being empty works before adding a book
     */
    @Test
    public void checkEmpty() throws Exception {
        // make sure current actiivty is addbook page
        solo.assertCurrentActivity("Wrong Activity", AddBook.class);

        // check if program prompts user for input if any of the edittexts are empty
        solo.clickOnButton("Add Book");
        assertTrue(solo.waitForText("Please enter title", 1, 2000));

        solo.enterText((EditText) solo.getView(R.id.new_title_editText), "Test Title");
        solo.clickOnButton("Add Book");
        assertTrue(solo.waitForText("Please enter author's name", 1, 2000));


        solo.enterText((EditText) solo.getView(R.id.new_author_editText), "Test Author");
        solo.clickOnButton("Add Book");
        assertTrue(solo.waitForText("Please enter valid ISBN", 1, 2000));
    }

    /**
     * Checks if books are added when 'Add Book' is clicked
     * @throws Exception
     */
    @Test
    public void checkAdded() throws Exception {
        // make sure current actiivty is addbook page
        solo.assertCurrentActivity("Wrong Activity", AddBook.class);

        // add test book
        solo.enterText((EditText) solo.getView(R.id.new_title_editText), "Test Add Title");
        solo.enterText((EditText) solo.getView(R.id.new_author_editText), "Test Add Author");
        solo.enterText((EditText) solo.getView(R.id.new_isbn_editText), "0000123456789");
        // confirm adding book
        solo.clickOnButton("Add Book");
        assertTrue(solo.waitForText("Book Added", 1, 10000));

        // make sure current page is the shelves page
        solo.assertCurrentActivity("Wrong Activity", Host.class);

        // go to books owned page and check if book has been added
        solo.clickOnButton("Books Owned");
        assertTrue(solo.waitForText("Test Add Title", 1, 5000));
        assertTrue(solo.waitForText("Test Add Author", 1, 5000));
        assertTrue(solo.waitForText("0000123456789", 1, 5000));

    }

    /**
     * Check if File Chooser opens
     * Makes sure the file chooser functionality works
     * @throws Exception
     */
    @Test
    public void checkFileChooser() throws Exception {
        solo.assertCurrentActivity("Wrong Activity", AddBook.class);
        ImageButton imageButton = (ImageButton) solo.getView("imageButton");
        solo.clickOnView(imageButton);
        solo.assertCurrentActivity("Wrong Activity", AddBookCoverActivity.class);
    }

    /**
     * Closes activities after tests
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
