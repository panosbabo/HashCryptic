package com.example.hashcryptic;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class ChecksumTest {

    @Rule
    public ActivityScenarioRule<Checksum> activityScenarioRule = new ActivityScenarioRule<>(Checksum.class);

    @Test
    public void ChecksumFileTest() {
        // Test implementation

        Espresso.onView(ViewMatchers.withId(R.id.spinner))
                .perform(ViewActions.click());

        // Select the item from the Spinner's adapter
        onData(Matchers.anything()).atPosition(3).perform(click());
//        onView(withId(R.id.button_choose_file)).perform(click());
//        onView(withText("txt")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.copyhash_value)).perform(click());
    }
}