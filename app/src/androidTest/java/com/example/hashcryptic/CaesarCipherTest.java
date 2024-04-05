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

import com.example.hashcryptic.ciphers.Caesar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class CaesarCipherTest {

    @Rule
    public ActivityScenarioRule<Caesar> activityScenarioRule = new ActivityScenarioRule<>(Caesar.class);


    @Test
    public void CaesarCipherEncryptTest() {
        // Test implementation

        // Testing input string
        String input = "hello world";
        // Expected output string
        String output = "JGNNQ YQTNF";

        onView(withId(R.id.encrypt_Caesar_text)).perform(clearText(), typeText(input));
        onView(withId(R.id.caesarKey_size)).perform(clearText(), typeText("2"));
        onView(withId(R.id.encrCaesar)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.caesar_msg_cntxt)).check(matches(withText(output)));
        onView(withId(R.id.copyCaesar)).perform(click());
    }

    @Test
    public void CaesarCipherDecryptTest() {
        // Test implementation

        // Testing input string
        String input = "jgnnq yqtnf";
        // Expected output string
        String output = "HELLO WORLD";

        onView(withId(R.id.encrypt_Caesar_text)).perform(clearText(), typeText(input));
        onView(withId(R.id.caesarKey_size)).perform(clearText(), typeText("2"));
        onView(withId(R.id.decrCaesar)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.caesar_msg_cntxt)).check(matches(withText(output)));
        onView(withId(R.id.copyCaesar)).perform(click());
    }
}
