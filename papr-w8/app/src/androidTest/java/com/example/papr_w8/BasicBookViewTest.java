package com.example.papr_w8;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.papr_w8.ProfilePack.EditProfile;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class BasicBookViewTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        solo.enterText((EditText) solo.getView((R.id.email)), "mazi@mazi.com");
        solo.enterText((EditText) solo.getView(R.id.password), "test123");
        solo.clickOnButton("Login");
        solo.waitForFragmentById(R.id.fragment);
        solo.clickOnImage(0);
    }


    /**
     * check if everything is presented correctly on profile page
     */
    @Test
    public void checkInfo(){
        solo.waitForFragmentById(R.id.shelves);
        solo.clickOnButton("Awaiting Approval");
        solo.waitForFragmentById(R.id.awaiting_approval);
        solo.waitForText("The Catcher in the Rye");
        solo.clickInList(0);
        solo.waitForFragmentById(R.id.fragment_book_basic);
        solo.waitForText("scottk");
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
