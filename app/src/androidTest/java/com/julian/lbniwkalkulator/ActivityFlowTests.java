package com.julian.lbniwkalkulator;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.julian.lbniwkalkulator.pages.StartViewActivity;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ActivityFlowTests {
    @Test
    public void testStartViewActivity() {
        ActivityScenario<StartViewActivity> scenario = ActivityScenario.launch(StartViewActivity.class);
        assertNotNull(scenario);
        scenario.close();
    }
    @Test
    public void testGoingFromStartToXRayActivity() {
        ActivityScenario<StartViewActivity> scenario = ActivityScenario.launch(StartViewActivity.class);
        Espresso.onView(withId(R.id.button_select_xray)).perform(click());
        Espresso.onView(withId(R.layout.x_ray_menu_layout)).check(matches(isDisplayed()));
        scenario.close();
    }
    @Test
    public void testGoingFromStartToIsotopeActivity() {
        ActivityScenario<StartViewActivity> scenario = ActivityScenario.launch(StartViewActivity.class);
        Espresso.onView(withId(R.id.button_select_isotope)).perform(click());
        Espresso.onView(withId(R.layout.isotope_menu_layout)).check(matches(isDisplayed()));
        scenario.close();
    }
}
