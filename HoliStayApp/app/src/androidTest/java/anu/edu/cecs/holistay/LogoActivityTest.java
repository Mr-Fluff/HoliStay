package anu.edu.cecs.holistay;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import anu.edu.cecs.holistay.ui.LogoActivity;

@RunWith(AndroidJUnit4.class)
public class LogoActivityTest {

    @Rule
    public ActivityScenarioRule<LogoActivity> activityRule = new ActivityScenarioRule<>(LogoActivity.class);

    @Test
    public void isActivityDisplayed() {
        onView(withId(R.id.logoAct)).check(matches(isDisplayed()));
    }

    @Test
    public void isLogoVisible() {
        onView(withId(R.id.hotelImage)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
