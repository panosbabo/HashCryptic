package com.example.hashcryptic;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public TestLogger testLogger = new TestLogger();

    @Test
    public void HomePageTest() {
        // Test implementation
        onView(withId(R.id.home)).perform(click());
    }

    @Test
    public void ProfilePageNavTest() {
        // Test implementation
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.id.editprofile_btn)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    @Test
    public void CipherPageNavTest() {
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
    public void HashPageNavTest() {
        // Test implementation
        onView(withId(R.id.hashcrypt)).perform(click());
        onView(withId(R.id.encryptBtn)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.decryptBtn)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.chksumBtn)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.encryptFileBtn)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.decryptFileBtn)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    @Test
    public void MyHashValuesTest() {
        // Test implementation
        onView(withId(R.id.hashvlsBtn)).perform(click());

//        onData(withId(R.id.idHashList)).onChildView(withId(R.id.copyencr_txt)).atPosition(0).perform(click());
//        onView(withId(R.id.qrcode_btn)).perform(click());
//        onView(isRoot()).perform(pressBack());
    }


    private static class TestLogger extends TestWatcher {
        @Override
        protected void succeeded(Description description) {
            System.out.println("Test passed: " + description.getDisplayName());
        }
    }
}

