package com.example.papr_w8;

import android.view.View;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.papr_w8.ProfilePack.EditProfile;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EditProfileTest {
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
        solo.enterText((EditText) solo.getView((R.id.email)), "mazi@mail.com");
        solo.enterText((EditText) solo.getView(R.id.password), "test123");
        solo.clickOnButton("Login");
        solo.waitForFragmentById(R.id.fragment);
        View profile = solo.getView("profile");
        solo.clickOnView(profile);
        solo.waitForFragmentById(R.id.profile);
        solo.clickOnButton("Edit Profile");
    }


    /**
     * check if system allows users to edit text
     * and if the edited information is updated
     * after test is run, the information will be changed back
     */
    @Test
    public void checkEnterText(){
        solo.assertCurrentActivity("Wrong Activity", EditProfile.class);
        solo.waitForText("Mazi");
        solo.waitForText("mazi@mail.com");
        solo.waitForText("Mazi st, Mazi ave.");
        solo.clearEditText((EditText) solo.getView(R.id.editUsername));
        solo.enterText((EditText) solo.getView((R.id.editUsername)), "PapaMazi");
        solo.clearEditText((EditText) solo.getView(R.id.editAddress));
        solo.enterText((EditText) solo.getView((R.id.editAddress)), "Mazi address");
        solo.clickOnButton("Confirm");

        solo.waitForFragmentById(R.id.profile);
        assertTrue(solo.waitForText("PapaMazi"));
        assertTrue(solo.waitForText("mazi@mail.com"));
        assertTrue(solo.waitForText("Mazi address"));

        solo.clickOnButton("Edit Profile");
        solo.assertCurrentActivity("Wrong Activity", EditProfile.class);
        solo.waitForText("Mazi");
        solo.waitForText("mazi@mail.com");
        solo.waitForText("Mazi st, Mazi ave.");
        solo.clearEditText((EditText) solo.getView(R.id.editUsername));
        solo.enterText((EditText) solo.getView((R.id.editUsername)), "Mazi");
        solo.clearEditText((EditText) solo.getView(R.id.editAddress));
        solo.enterText((EditText) solo.getView((R.id.editAddress)), "Mazi st, Mazi ave.");
        solo.clickOnButton("Confirm");
    }

    /**
     * check if cancel button works properly
     */
    @Test
    public void checkCancel(){
        solo.assertCurrentActivity("Wrong Activity", EditProfile.class);
        solo.clickOnButton("Cancel");
        solo.waitForFragmentById(R.id.profile);
        assertTrue(solo.waitForText("Mazi"));
        assertTrue(solo.waitForText("mazi@mail.com"));
        assertTrue(solo.waitForText("Mazi st, Mazi ave."));
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
