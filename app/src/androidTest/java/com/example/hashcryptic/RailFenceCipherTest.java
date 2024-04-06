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
import com.example.hashcryptic.ciphers.RailFence;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class RailFenceCipherTest {

    @Rule
    public ActivityScenarioRule<RailFence> activityScenarioRule = new ActivityScenarioRule<>(RailFence.class);

    @Test
    public void RailFenceCipherEncryptTest() {
        // Test implementation

        // Testing input string
        String input = "meet me after the toga party";
        int key = 2;

        // Expected output string
        String output = "MEMATRHTGPRYETEFETEOAAT";

        onView(withId(R.id.encrypt_RailFence_text)).perform(clearText(), typeText(input));
        onView(withId(R.id.railRow_size)).perform(clearText(), typeText(String.valueOf(key)));
        onView(withId(R.id.encrRailFence)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.rail_msg_cntxt)).check(matches(withText(output)));
        onView(withId(R.id.copyRailFence)).perform(click());
    }

    @Test
    public void RailFenceCipherDecryptTest() {
        // Test implementation

        // Testing input string
        String input = "mematrhtgpryetefeteoaat";
        int key = 2;

        // Expected output string
        String output = "MEETMEAFTERTHETOGAPARTY";

        onView(withId(R.id.encrypt_RailFence_text)).perform(clearText(), typeText(input));
        onView(withId(R.id.railRow_size)).perform(clearText(), typeText(String.valueOf(key)));
        onView(withId(R.id.decrRailFence)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.rail_msg_cntxt)).check(matches(withText(output)));
        onView(withId(R.id.copyRailFence)).perform(click());
    }
}
