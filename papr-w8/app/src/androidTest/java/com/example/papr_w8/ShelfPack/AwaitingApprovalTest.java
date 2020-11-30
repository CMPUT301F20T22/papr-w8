package com.example.papr_w8.ShelfPack;

import android.view.View;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.papr_w8.Host;
import com.example.papr_w8.MainActivity;
import com.example.papr_w8.R;
import com.robotium.solo.Solo;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AwaitingApprovalTest  {
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
        //Login with a test account
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.enterText((EditText) solo.getView((R.id.email)), "scott_test_acc@test.com");
        solo.enterText((EditText) solo.getView(R.id.password), "guest1");
        solo.clickOnButton("Login");
        solo.waitForFragmentById(R.id.fragment);
        //click on the shelves icon on nav bar
        View shelves = solo.getView("shelves");
        solo.clickOnImage(0);
        solo.clickOnView(shelves);
        //check for shelve page/ host activity
        solo.waitForFragmentById(R.id.shelves);
        solo.assertCurrentActivity("Wrong Activity", Host.class);
        //go to books owned fragment
        solo.clickOnButton("Awaiting Approval");
        //check for Text
        solo.getText("Awaiting Approval", false);
    }

    @Test
    public void checkListView(){
        //assert awaiting approval view
        View awaitingApprovalView = solo.getView("awaiting_approval");
        //Check for list of awaiting approvals
        View awaitingApprovalListView = solo.getView("books_awaiting_list");
        //click on list of borrowed books
        solo.clickOnView(awaitingApprovalListView);
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