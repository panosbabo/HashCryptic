package com.example.hashcryptic;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.hashcryptic.ciphers.HillCipher;
import com.example.hashcryptic.ciphers.RailFence;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class HillCipherTest {

    @Rule
    public ActivityScenarioRule<HillCipher> activityScenarioRule = new ActivityScenarioRule<>(HillCipher.class);

    @Test
    public void HillCipherEncryptTest() {
        // Test implementation

        // Testing input string
        String input = "cake";
        String key = "bake";

        // Expected output string
        String output = "CUKM";

        onView(withId(R.id.encrypt_Hill_text)).perform(clearText(), typeText(input));
        onView(withId(R.id.hillKey)).perform(clearText(), typeText(key));
        onView(withId(R.id.encrHill)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.hill_msg_cntxt)).check(matches(withText(output)));
        onView(withId(R.id.copyHill)).perform(click());
    }

    @Test
    public void HillCipherDecryptTest() {
        // Test implementation

        // Testing input string
        String input = "cake";
        String key = "dagj";

        // Expected output string
        String output = "SOME";

        onView(withId(R.id.encrypt_Hill_text)).perform(clearText(), typeText(input));
        onView(withId(R.id.hillKey)).perform(clearText(), typeText(key));
        onView(withId(R.id.decrHill)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.hill_msg_cntxt)).check(matches(withText(output)));
        onView(withId(R.id.copyHill)).perform(click());
    }
}
