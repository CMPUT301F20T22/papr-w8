package com.example.papr_w8.ShelfPack;


import android.view.View;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.papr_w8.Host;
import com.example.papr_w8.MainActivity;
import com.example.papr_w8.ProfilePack.EditProfile;
import com.example.papr_w8.R;
import com.robotium.solo.Solo;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Test the functionality of the shelves page
 */

public class ShelvesTest  {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        //Login with a test account
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        solo.enterText((EditText) solo.getView((R.id.email)), "scott_testacc@test.com");
        solo.enterText((EditText) solo.getView(R.id.password), "guest1");
        solo.clickOnButton("Login");
        solo.waitForFragmentById(R.id.fragment);
        //click on the shelves icon on nav bar
        View shelves = solo.getView("shelves");
        solo.clickOnImage(0);
        solo.clickOnView(shelves);
    }

    /**
     * confirm the existence of all required buttons
     */
    @Test
    public void checkButtons(){
        //check for buttons
        solo.waitForFragmentById(R.id.shelves);

        solo.getButton("Books Owned",false);
        solo.getButton("Books Requested",false);
        solo.getButton("Accepted Requests",false);
        solo.getButton("Awaiting Approval",false);
        solo.getButton("Books Borrowed",false);
        assertTrue(solo.waitForText("Books Owned",1,2000));
        assertTrue(solo.waitForText("Books Requested",1,2000));
        assertTrue(solo.waitForText("Accepted Requests",1,2000));
        assertTrue(solo.waitForText("Awaiting Approval",1,2000));
        assertTrue(solo.waitForText("Books Borrowed",1,2000));
        //check that we are still in the host class
        solo.assertCurrentActivity("Wrong Activity", Host.class);
    }

    /**
     * Confirm the existence of reuired text
     */
    @Test
    public void checkText(){
        //check for Text
        solo.waitForFragmentById(R.id.shelves);
        solo.getText("Owner",false);
        solo.getText("Borrower",false);
        assertTrue(solo.waitForText("Owner",1,2000));
        assertTrue(solo.waitForText("Borrower",1,2000));
        solo.assertCurrentActivity("Wrong Activity", Host.class);
    }


    /**
     * Close activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}