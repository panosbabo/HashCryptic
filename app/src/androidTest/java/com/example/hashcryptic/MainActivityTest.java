package com.example.hashcryptic;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public TestLogger testLogger = new TestLogger();

//        onView(withId(R.id.encrypt)).perform(click());
//        onView(withId(R.id.encryptBtn)).perform(click());
//        onView(isRoot()).perform(ViewActions.pressBack());
//        onView(withText("Hello world!")).check(matches(isDisplayed()));

    @Test
    public void HomePageTest() {
        // Test implementation
        onView(withId(R.id.home)).perform(click());
        testLogger.succeeded(Description.TEST_MECHANISM);
    }

    @Test
    public void ProfilePageTest() {
        // Test implementation
        onView(withId(R.id.profile)).perform(click());
    }

    @Test
    public void CipherPagesTest() {
        // Test implementation
        onView(withId(R.id.hashcrypt)).perform(click());
        onView(withId(R.id.ciphersBtn)).perform(click());
        onView(withId(R.id.caesarCiph_btn)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.vigenereCiph_btn)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.railFenceCiph_btn)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.hillCiph_btn)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    @Test
    public void MyHashValuesTest() {
        // Test implementation
        onView(withId(R.id.hashvlsBtn)).perform(click());
    }


    private static class TestLogger extends TestWatcher {
        @Override
        protected void succeeded(Description description) {
            System.out.println("Test passed: " + description.getDisplayName());
        }
    }
}

