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

public class SignUpActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<SignUpActivity> rule =
            new ActivityTestRule<>(SignUpActivity.class,true,true);

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
     * Checks if switches to Host acitivity on a successful sign up
     */
    @Test
    public void checkActivitySwitch(){
        // Asserts that the current activity is the SignUpActivity. Otherwise, show "Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editTextUserName), "test3");
        solo.enterText((EditText) solo.getView(R.id.editTextEmailAddress), "test3@mail.com");
        solo.enterText((EditText) solo.getView(R.id.editTextPassword), "password2");
        solo.enterText((EditText) solo.getView(R.id.editTextTextPostalAddress), "somewhere");
        solo.clickOnButton("SIGN UP");

        // checks if new activity is Host activity
        solo.waitForActivity(Host.class);
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
