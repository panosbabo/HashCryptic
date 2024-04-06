package com.example.hashcryptic;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class ProfileEditTest {

    @Rule
    public ActivityScenarioRule<ProfileEditing> activityScenarioRule = new ActivityScenarioRule<>(ProfileEditing.class);

    @Test
    public void ProfileEditPageTest() {
        // Test implementation
        String username = "panosbabo";
        String email = "panos@panos.com";
        int age = 33;

        onView(withId(R.id.usernameinsert)).perform(clearText(), typeText(username));
        onView(withId(R.id.emailinsert)).perform(clearText(), typeText(email));
        onView(withId(R.id.ageinsert)).perform(clearText(), typeText(String.valueOf(age)));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.update_prof_btn)).perform(click());

    }
}
