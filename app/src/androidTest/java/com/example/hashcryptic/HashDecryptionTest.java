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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class HashDecryptionTest {

    @Rule
    public ActivityScenarioRule<DecryptText> activityScenarioRule = new ActivityScenarioRule<>(DecryptText.class);

    @Test
    public void HashDecryptTest() {
        // Test implementation

        // Testing input string
        String input = "a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146e";
        // Expected output string SHA-256
        String output = "Hello World";

        onView(withId(R.id.decrypt_editText)).perform(clearText(), typeText(input));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.buttonDecrypt)).perform(click());
        onView(withId(R.id.decr_msg_cntxt)).check(matches(withText(output)));
        onView(withId(R.id.copydecr_txt)).perform(click());

    }
}
