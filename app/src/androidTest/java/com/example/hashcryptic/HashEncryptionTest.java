package com.example.hashcryptic;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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
public class HashEncryptionTest {

    @Rule
    public ActivityScenarioRule<EncryptText> activityScenarioRule = new ActivityScenarioRule<>(EncryptText.class);

    @Test
    public void HashEncryptTest() {
        // Test implementation

        // Testing input string
        String input = "Hello World";
        // Expected output string SHA-256
        String output = "a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146e";

        Espresso.onView(ViewMatchers.withId(R.id.spinner))
                .perform(ViewActions.click());

        // Select the item from the Spinner's adapter
        onData(Matchers.anything()).atPosition(3).perform(click());
        onView(withId(R.id.encrpt_editText)).perform(clearText(), typeText(input));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.buttonEncrypt)).perform(click());
        onView(withId(R.id.encr_msg_cntxt)).check(matches(withText(output)));
        onView(withId(R.id.copyencr_txt)).perform(click());
//        onView(withId(R.id.share_hash)).perform(click());
//        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.store_hash)).perform(click());

    }

    @Test
    public void HashEncryptWithCompressionTest() {
        // Test implementation

        // Testing input string
        String input = "Hello World";
        // Expected output string SHA-256
        String output = "276d8f72b56fe3d4ba6a9773ece70c7c6e9156eb31ab4270b7978bac71a2d379";

        Espresso.onView(ViewMatchers.withId(R.id.spinner))
                .perform(ViewActions.click());

        // Select the item from the Spinner's adapter
        onData(Matchers.anything()).atPosition(3).perform(click());
        onView(withId(R.id.compressionSwitch)).perform(click());
        onView(withId(R.id.encrpt_editText)).perform(clearText(), typeText(input));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.buttonEncrypt)).perform(click());
        onView(withId(R.id.encr_msg_cntxt)).check(matches(withText(output)));
        onView(withId(R.id.copyencr_txt)).perform(click());
//        onView(withId(R.id.share_hash)).perform(click());
//        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.store_hash)).perform(click());

    }

}
