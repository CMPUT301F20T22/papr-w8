package com.example.papr_w8.Search;

/**
 * Tests all 3 search functionalities
 * Starts from the host activity that does not require logging in
 * requires Robinson Crusoe book and scotta test account to work
 */

import static org.junit.Assert.assertTrue;
import android.view.View;
import android.widget.EditText;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.example.papr_w8.Host;
import com.example.papr_w8.R;
import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SearchTestBase {
    private Solo solo;

    @Rule
    public ActivityTestRule<Host> rule = new ActivityTestRule<Host>(Host.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", Host.class);
        solo.waitForFragmentById(R.id.fragment);
        View search = solo.getView("search");
        solo.clickOnImage(2);
    }


    /**
     * Test the functionality of searching for an available book
     * @throws InterruptedException
     */
    @Test
    public void searchForAvailableBook() throws InterruptedException {
        //search for "Robinson Crusoe"
        solo.enterText((EditText) solo.getView((R.id.search_edit_text)), "Robinson Crusoe");
        assertTrue(solo.waitForText("Robinson Crusoe",1,2000));
        // Click on result list
        solo.clickOnView(solo.getView(R.id.result_list));
        //click on book
        solo.clickOnText("Robinson Crusoe");
        //Assert that the book is available
        assertTrue(solo.waitForText("Available",1,2000));
        assertTrue(solo.waitForText("Robinson Crusoe",1,2000));
        //view the book
        solo.waitForFragmentById(R.id.search);
    }

    /**
     * Test the functionality for searching for a user
     * @throws InterruptedException
     */
    @Test
    public void searchForUser() throws InterruptedException{
        //click on dropDown menu
        solo.clickOnText("Ava. Books");
        //select the "Users option
        solo.clickOnText("Users");
        //Assert user search
        assertTrue(solo.waitForText("Users",1,2000));
        //search for Scotta
        solo.enterText((EditText) solo.getView((R.id.search_edit_text)), "Scotta");
        assertTrue(solo.waitForText("scott_test_acc@test.com",1,2000));
        //click on the user
        solo.clickOnText("scott_test_acc@test.com");
        // assert that it is the correct user
        assertTrue(solo.waitForText("Scotta",1,2000));
        //assert that the user profile page has been navigated to
        assertTrue(solo.waitForText("User Profile",1,2000));
        solo.waitForFragmentById(R.id.search);
    }

    /**
     * Test the functionality for searching for all books
     * @throws InterruptedException
     */
    @Test
    public void searchForAllBooks() throws InterruptedException{
        //click on dropDown menu
        solo.clickOnText("Ava. Books");
        //select the All books option
        solo.clickOnText("All Books");
        //Assert all book status can be seen

        if (solo.waitForText("Available",1,2000)){
                assertTrue(solo.waitForText("Available", 1, 2000));
                //click on the Available book
                solo.clickOnText("Available");
                //verify that we are on the Description page
                assertTrue(solo.waitForText("Description",1,2000));
                //assert the book is available
                solo.waitForText("Available", 1, 2000);
        }

            solo.waitForFragmentById(R.id.search);

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