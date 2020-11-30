package com.example.papr_w8.BookView;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.papr_w8.Host;
import com.example.papr_w8.MainActivity;
import com.example.papr_w8.R;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 *  tests the delete functionality for books
 */
public class ConfirmDeleteTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates a solo instance
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
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
        solo.enterText((EditText) solo.getView(R.id.new_title_editText), "Delete Book Title");
        solo.enterText((EditText) solo.getView(R.id.new_author_editText), "Delete Book Author");
        solo.enterText((EditText) solo.getView(R.id.new_isbn_editText), "12345678912");
        solo.clickOnButton("Add Book");
//        assertTrue(solo.waitForText("Book Added", 1, 15000));
        //return to shelves after adding a book
    }

    /**
     * checks if books are actually deleted
     * @throws Exception
     */
    @Test
    public void checkDelete() throws Exception {
        // checks if current activity is the shelves page
        solo.assertCurrentActivity("Wrong Activity", Host.class);

        // switch to books owned page and check if book added it there
        solo.clickOnButton("Books Owned");
        assertTrue(solo.waitForText("Delete Book Title", 1, 5000));
        assertTrue(solo.waitForText("Delete Book Author", 1, 5000));
        assertTrue(solo.waitForText("12345678912", 1, 5000));

        // click on the first book and delete
        solo.clickInList(0);
        solo.clickOnButton("Delete Book");
        // wait for the delete fragment to show
        solo.waitForFragmentById(R.id.delete_fragment);
        // click on the confirm delete button
        solo.clickOnView(solo.getView(R.id.confirm_deletion));
        // go back to Books Owned page and make sure book has been deleted
        solo.assertCurrentActivity("Wrong Activity", Host.class);
        solo.clickOnButton("Books Owned");
        assertTrue(solo.searchText("Delete Book Title", 0, false));
        assertTrue(solo.searchText("Delete Book Author", 0, false));
        assertTrue(solo.searchText("12345678912", 0, false));
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