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
public class EditBookTest {
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
    public void checkEmpty() {
        solo.assertCurrentActivity("Wrong Activity", AddBook.class);

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
     */
    @Test
    public void checkAdded() {
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

    }

    /**
     * Check if File Chooser opens
     */
    @Test
    public void checkFileChooser() {
        solo.assertCurrentActivity("Wrong Activity", AddBook.class);
        ImageButton imageButton = (ImageButton) solo.getView("imageButton");
        solo.clickOnView(imageButton);
        solo.assertCurrentActivity("Wrong Activity", AddBookCoverActivity.class);
    }

    /**
     * Closes activites after tests
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
