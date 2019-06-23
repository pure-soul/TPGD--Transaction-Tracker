package com.example.rhadjni.lifeplanner.Activities;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.rhadjni.lifeplanner.R;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.EasyMock2Matchers.equalTo;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    public static Matcher<Object> withItemContent(String expectedText) {
        checkNotNull(expectedText);
        return withItemContent(equalTo(expectedText));
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

/*
USE CASE FOR DELETING A TRANSACTION WITH PREREQUISITE "ADD TRANSACTION". ERRORS INCLUDE

1. Crashing if no fields are entered
2. Crashing if name is entered and form submitted
3. Crashing if name and date is entered and form submitted
4. No date is shown when entered
 */
    @Test
    public void deleteTransaction() throws Exception{
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.enter_name)).perform(click()).perform(clearText()).perform(typeText("Bricks"));
        onView(withId(R.id.enter_quantity)).perform(typeText("100"));
        //pressBack();
        onView(withId(R.id.task_submit)).perform(click());
        onView(withText("OK")).perform(longClick());
        onView(withText("ADD TRANSACTION")).perform(longClick());

        //Click the delete_product pencil icon
        onView(withId(R.id.product_list)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Bricks")),MyViewAction.clickChildViewWithId(R.id.delete_product)));
        onView(withText("YES")).perform(longClick());
        onView(withId(R.id.Emptyex)).perform(click());
        onView(withText("YES")).perform(click());
        onView(withId(R.id.Emptytrans)).perform(click());
        onView(withText("YES")).perform(click());

    }

    @Test
    public void deleteTransaction2() throws Exception{
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.enter_name)).perform(click()).perform(clearText()).perform(typeText("Bricks"));
        onView(withText("ADD TRANSACTION")).perform(longClick());
    }

    @Test
    public void deleteTransaction3() throws Exception{
        onView(withId(R.id.fab)).perform(click());
        onView(withText("ADD TRANSACTION")).perform(longClick());
    }

    @Test
    public void deleteTransaction4() throws Exception{
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.enter_name)).perform(click()).perform(clearText()).perform(typeText("Bricks"));
        pressBack();
        onView(withId(R.id.task_submit)).perform(click());
        onView(withText("OK")).perform(longClick());
        onView(withText("ADD TRANSACTION")).perform(longClick());
    }
/*
USE CASE FOR ENTERING A BUDGET.
1. App crashes when an expense is entered with no income.
2. No error checking present for when no value is entered into income field.
3. Expense values higher than income trigger no error to prevent negative figures in the pie chart
4. App crashes when an expense isn't entered and attempt is made to submit
*/

    @Test
    public void enterBudget() throws Exception{
        onView(withId(R.id.expense)).perform(click());
        onView(withId(R.id.prompter)).perform(click());
        onView(withId(R.id.enter_inc)).perform(click()).perform(typeText("200"));
        onView(withText("ADD INCOME")).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.enter_name)).perform(click()).perform(typeText("Sax"));
        onView(withId(R.id.enter_quantity)).perform(click()).perform(typeText("150"));
        onView(withText("ADD EXPENSE")).perform(longClick());
        onView(withId(R.id.Emptyex)).perform(click());
        onView(withText("YES")).perform(click());
        onView(withId(R.id.Emptytrans)).perform(click());
        onView(withText("YES")).perform(click());
    }

    @Test
    public void enterBudget2() throws Exception{
        onView(withId(R.id.expense)).perform(click());
        onView(withId(R.id.prompter)).perform(click());
        onView(withText("ADD INCOME")).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.enter_name)).perform(click()).perform(typeText("Sax"));
        onView(withId(R.id.enter_quantity)).perform(click()).perform(typeText("150"));
        onView(withText("ADD EXPENSE")).perform(longClick());
    }

    @Test
    public void enterBudget3() throws Exception{
        onView(withId(R.id.expense)).perform(click());
        onView(withId(R.id.prompter)).perform(click());
        onView(withId(R.id.enter_inc)).perform(click()).perform(typeText("200"));
        onView(withText("ADD INCOME")).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.enter_name)).perform(click()).perform(typeText("Sax"));
        onView(withId(R.id.enter_quantity)).perform(click()).perform(typeText("400"));
        onView(withText("ADD EXPENSE")).perform(longClick());
    }

    @Test
    public void enterBudget4() throws Exception{
        onView(withId(R.id.expense)).perform(click());
        onView(withId(R.id.prompter)).perform(click());
        onView(withId(R.id.enter_inc)).perform(click()).perform(typeText("200"));
        onView(withText("ADD INCOME")).perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withText("ADD EXPENSE")).perform(longClick());
    }
/*
USE CASE FOR DELETING AN EXPENSE
 */
    @Test
    public void func_deleteExpense() throws Exception{
        onView(withId(R.id.expense)).perform(click());
        onView(withId(R.id.prompter)).perform(click());
        onView(withId(R.id.enter_inc)).perform(click()).perform(typeText("200"));
        onView(withText("ADD INCOME")).perform(click());
        //Press Plus button
        onView(withId(R.id.fab)).perform(click());
        //Add title of expense
        onView(withId(R.id.enter_name)).perform(click()).perform(typeText("Spurlina"));
        //Add amount spent
        onView(withId(R.id.enter_quantity)).perform(click()).perform(typeText("25"));
        //Add expense
        onView(withText("ADD EXPENSE")).perform(longClick());
        onView(withId(R.id.product_list)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Spurlina")),MyViewAction.clickChildViewWithId(R.id.delete_product)));
        onView(withText("YES")).perform(longClick());
    }

/*
On smaller screens, the functionality to wipe the database is not present
 */

}