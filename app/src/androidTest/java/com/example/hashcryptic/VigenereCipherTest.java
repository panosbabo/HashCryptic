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

import com.example.hashcryptic.ciphers.Vigenere;

import org.junit.Rule;
import org.junit.Test;

public class VigenereCipherTest {

    @Rule
    public ActivityScenarioRule<Vigenere> activityScenarioRule = new ActivityScenarioRule<>(Vigenere.class);

    @Test
    public void VigenereCipherEncryptTest() {
        // Test implementation

        // Testing input string
        String input = "hello world";
        String key = "panos";

        // Expected output string
        String output = "WEYZG LOEZV";

        onView(withId(R.id.encrypt_Vigenere_text)).perform(clearText(), typeText(input));
        onView(withId(R.id.vigenereKey_size)).perform(clearText(), typeText(key));
        onView(withId(R.id.encrVigenere)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.vgnr_msg_cntxt)).check(matches(withText(output)));
        onView(withId(R.id.copyVignr)).perform(click());
    }

    @Test
    public void VigenereCipherDecryptTest() {
        // Test implementation

        // Testing input string
        String input = "weyzg loezv";
        String key = "panos";

        // Expected output string
        String output = "HELLO WORLD";

        onView(withId(R.id.encrypt_Vigenere_text)).perform(clearText(), typeText(input));
        onView(withId(R.id.vigenereKey_size)).perform(clearText(), typeText(key));
        onView(withId(R.id.decrVigenere)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.vgnr_msg_cntxt)).check(matches(withText(output)));
        onView(withId(R.id.copyVignr)).perform(click());
    }
}
