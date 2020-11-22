package com.example.papr_w8;

import android.app.Activity;
import android.app.Fragment;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SearchTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<Host> rule =
            new ActivityTestRule<>(Host.class,true,true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

//    /**
//     * Gets the Activity
//     * @throws Exception
//     */
//    @Test
//    public void start() throws Exception{
//        Activity activity = rule.getActivity();
//    }

    @Test
    public void checkAvailableBooksFragmentSwitch(){
        Activity current = solo.getCurrentActivity(); // opens nav bar
//        Fragment fragment = current.getFragmentManager().findFragmentByTag("fragment_search_tag");
        solo.assertCurrentActivity("Wrong Activity", Host.class);

        // click on search button in nav bar
//        solo.clickOnActionBarItem(R.id.search);
//        solo.pressMenuItem(1)
//        solo.clickOnActionBarItem(R.id.search);
//        solo.clickOnScreen(100,500);
//        solo.clickOnText("search_nav");
//        solo.clickOnMenuItem("search_nav");
        solo.clickOnImage(2);
        solo.pressSpinnerItem(0,1);


        // click on spinner

        // check if fragment switched
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
