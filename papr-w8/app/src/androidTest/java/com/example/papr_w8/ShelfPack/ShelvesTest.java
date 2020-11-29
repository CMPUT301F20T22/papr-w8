package com.example.papr_w8.ShelfPack;

import android.util.Log;
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
        //Login with a test acocunt
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
    @Test
    public void checkButtons(){
        //check for buttons
        solo.waitForFragmentById(R.id.shelves);

        solo.getButton("Books Owned",false);
        solo.getButton("Books Requested",false);
        solo.getButton("Accepted Requests",false);
        solo.getButton("Awaiting Approval",false);
        solo.getButton("Books Borrowed",false);
        //check that we are still in the host class
        solo.assertCurrentActivity("Wrong Activity", Host.class);
    }
    @Test
    public void checkText(){
        //check for Text
        solo.waitForFragmentById(R.id.shelves);
        solo.getText("Owner",false);
        solo.getText("Borrower",false);
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