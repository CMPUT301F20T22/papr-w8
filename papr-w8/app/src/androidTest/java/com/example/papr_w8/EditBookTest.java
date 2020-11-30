package com.example.papr_w8;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

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
        solo.clickOnView(solo.getView(R.id.add_book_float));
        solo.enterText((EditText) solo.getView(R.id.new_title_editText), "Edit Title");
        solo.enterText((EditText) solo.getView(R.id.new_author_editText), "Edit Author");
        solo.enterText((EditText) solo.getView(R.id.new_isbn_editText), "1234567890000");
        solo.clickOnButton("Add Book");
        assertTrue(solo.waitForText("Book Added", 1, 10000));
        //return to shelves after adding a book
    }


    /**
     * Checks if books are edited
     * edits the book that was added in the setUp() then makes sure the edits are shown in BooksOwned
     * @throws Exception
     */
    @Test
    public void checkEdit() throws Exception{
        // checks if current activity is the shelves page
        solo.assertCurrentActivity("Wrong Activity", Host.class);

        // switch to books owned page and check if book added it there
        solo.clickOnButton("Books Owned");
        assertTrue(solo.waitForText("Edit Title", 1, 5000));
        assertTrue(solo.waitForText("Edit Author", 1, 5000));
        assertTrue(solo.waitForText("1234567890000", 1, 5000));

        // click on the first book and edit
        solo.clickInList(0);
        solo.clickOnButton("Edit Description");
        // clear editTexts
        solo.clearEditText((EditText) solo.getView(R.id.edit_title_editText));
        solo.clearEditText((EditText) solo.getView(R.id.edit_author_editText));
        solo.clearEditText((EditText) solo.getView(R.id.edit_isbn_editText));
        //input edited book desc
        solo.enterText((EditText) solo.getView(R.id.edit_title_editText), "Edited Title Test");
        solo.enterText((EditText) solo.getView(R.id.edit_author_editText), "Edited Author Test");
        solo.enterText((EditText) solo.getView(R.id.edit_isbn_editText), "0000123456789");
        solo.clickOnButton("Confirm");
        assertTrue(solo.waitForText("Book Edited", 1, 10000));
        // after editing book should go back to shelves page
        solo.assertCurrentActivity("Wrong Activity", Host.class);
        // go back to Books Owned and check if book has been edited
        solo.clickOnButton("Books Owned");
        assertTrue(solo.waitForText("Edited Title Test", 1, 2000));
        assertTrue(solo.waitForText("Edited Author Test", 1, 2000));
        assertTrue(solo.waitForText("0000123456789", 1, 2000));
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
