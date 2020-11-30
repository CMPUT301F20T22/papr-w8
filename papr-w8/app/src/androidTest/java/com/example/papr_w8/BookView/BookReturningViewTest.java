package com.example.papr_w8.BookView;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    }

    @Test
    public void checkFragment() throws Exception {

        solo.clickInList(0);
        solo.waitForFragmentById(R.id.book_returning);


    }

    @Test
    public void checkInformation() throws Exception {

        //final TextView titleView = solo.getView(R.)

        solo.waitForText("A Clockwork Orange");
        solo.waitForText("Anthony Burgess");
        solo.waitForText("Borrowed");
        solo.waitForText("9780393341768");
        solo.waitForText("mazi@mail.com");

    }



    @Test
    public void checkCancel() throws Exception {

        solo.clickOnButton("Cancel");




    }

    @Test
    public void checkReturn() throws Exception {

        solo.clickOnButton("Books Borrowed");
        solo.clickInList(0);
        solo.clickOnButton("Return Book");


    }


    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
