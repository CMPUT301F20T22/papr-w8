package com.example.papr_w8.BookView;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class BookAwaitingApprovalTest {

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
        solo.clickOnButton("Awaiting Approval");
        solo.clickInList(0);
    }

    /**
     * Check that the current fragment is for Book Returning
     * @throws Exception
     */
    @Test
    public void checkFragment() throws Exception {

        solo.waitForFragmentById(R.id.fragment_book_base);

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
        final ImageView coverView = (ImageView) solo.getView(R.id.book_base_cover);

        assertEquals("The Handmaid's Tale", titleView.getText().toString());
        assertEquals("Margaret Atwood", authorView.getText().toString());
        assertEquals("Requested", statusView.getText().toString());
        assertEquals("9781480560109", isbnView.getText().toString());
        assertEquals("mazi@mail.com", ownerView.getText().toString());
        assertEquals(true, coverView.isShown());

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
