package com.example.papr_w8;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MainActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class,true,true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    /**
     * Checks if switches to Host activity after successful sign in
     */
    @Test
    public void checkActivitySwitch(){
        // Asserts that the current activity is the SignUpActivity. Otherwise, show "Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.email), "test4@mail.com");
        solo.enterText((EditText) solo.getView(R.id.password), "password4");
        solo.clickOnButton("Login");

        // checks if new activity is Host activity
        solo.waitForActivity(Host.class);
        solo.assertCurrentActivity("Wrong Activity", Host.class);
    }
    /**
     * If incorrect email or password is entered, unsuccessful login
     */
    @Test
    public void checkNoSwitch(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.email), "test1@email.com");
        solo.enterText((EditText) solo.getView(R.id.password), "password4");
        solo.clickOnButton("Login");

        // checks if new activity is Host activity
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
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