package com.example.papr_w8.BookView;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.papr_w8.Host;
import com.example.papr_w8.MainActivity;
import com.example.papr_w8.R;
import com.example.papr_w8.ScanActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookReturningViewTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class, true, true);
    private Object ListView;

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Login with a test account
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.enterText((EditText) solo.getView((R.id.email)), "fraser@mail.com");
        solo.enterText((EditText) solo.getView(R.id.password), "test123");
        solo.clickOnButton("Login");
        solo.waitForFragmentById(R.id.fragment);

        // Click on the shelves icon on nav bar
        View shelves = solo.getView("shelves");
        solo.clickOnImage(0);
        solo.clickOnView(shelves);

        // Check for shelve page/ host activity
        solo.waitForFragmentById(R.id.shelves);

        // Go to books owned fragment
        solo.clickOnButton("Books Borrowed");

        solo.clickInList(0);
    }

    /**
     * Check that the current fragment is for Book Returning
     * @throws Exception
     */
    @Test
    public void checkFragment() throws Exception {

        solo.waitForFragmentById(R.id.book_returning);

    }

    /**
     * Check that the information displayed is accurate and in the correct location
     * @throws Exception
     */
    @Test
    public void checkInformation() throws Exception {

        final TextView titleView = (TextView) solo.getView(R.id.titleEditText);
        final TextView authorView = (TextView) solo.getView(R.id.authorEditText);
        final TextView isbnView = (TextView) solo.getView(R.id.isbnEditText);
        final TextView statusView = (TextView) solo.getView(R.id.statusEditText);
        final TextView ownerView = (TextView) solo.getView(R.id.ownerEditText);

        assertEquals("A Clockwork Orange", titleView.getText().toString());
        assertEquals("Anthony Burgess", authorView.getText().toString());
        assertEquals("Borrowed", statusView.getText().toString());
        assertEquals("9780393341768", isbnView.getText().toString());
        assertEquals("mazi@mail.com", ownerView.getText().toString());

    }


    /**
     * Check that the cancel button returns the fragment to the host page
     * @throws Exception
     */
    @Test
    public void checkCancel() throws Exception {

        solo.clickOnButton("Cancel");
        solo.waitForFragmentById(R.id.fragment);

    }

    /**
     * Check that the return button opens the scan activity
     * @throws Exception
     */
    @Test
    public void checkReturn() throws Exception {

        solo.clickOnButton("Return Book");

        Activity activity = rule.getActivity();
        solo.assertCurrentActivity("Wrong Activity", ScanActivity.class);

    }

    /**
     * Close activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
