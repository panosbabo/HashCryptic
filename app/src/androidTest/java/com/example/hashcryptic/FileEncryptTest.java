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
public class FileEncryptTest {

    @Rule
    public ActivityScenarioRule<FileEncrypt> activityScenarioRule = new ActivityScenarioRule<>(FileEncrypt.class);

    @Test
    public void EncryptFileTest() {
        // Test implementation

        Espresso.onView(ViewMatchers.withId(R.id.spinner))
                .perform(ViewActions.click());

        // Select the item from the Spinner's adapter
        onData(Matchers.anything()).atPosition(0).perform(click());
        onView(withId(R.id.personalKeySwitch)).perform(click());
        onView(withId(R.id.compressionKeySwitch)).perform(click());
//        onView(withId(R.id.button_choose_file)).perform(click());
//        onView(withText("test.txt")).check(matches(isDisplayed())).perform(click());
    }

}
