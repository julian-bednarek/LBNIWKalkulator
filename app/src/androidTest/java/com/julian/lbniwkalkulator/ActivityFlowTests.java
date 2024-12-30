package com.julian.lbniwkalkulator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.julian.lbniwkalkulator.util.ErrorHandler.ERROR_BUTTON_TEXT;
import static org.hamcrest.Matchers.allOf;


import android.view.View;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import com.julian.lbniwkalkulator.pages.StartViewActivity;
import com.julian.lbniwkalkulator.pages.XRayMenuViewActivity;
import com.julian.lbniwkalkulator.util.StringGetter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ActivityFlowTests {
    private UiDevice device;

    @Before
    public void disableAnimations() {
        try {
            device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            device.executeShellCommand("settings put global window_animation_scale 0");
            device.executeShellCommand("settings put global transition_animation_scale 0");
            device.executeShellCommand("settings put global animator_duration_scale 0");
        } catch (Exception ignored) {}
    }

    @After
    public void restoreAnimations() {
        try {
            device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            device.executeShellCommand("settings put global window_animation_scale 1");
            device.executeShellCommand("settings put global transition_animation_scale 1");
            device.executeShellCommand("settings put global animator_duration_scale 1");
        } catch (Exception ignored) {}
    }

    @Test
    public void testStartViewActivity() {
        ActivityScenario<StartViewActivity> scenario = ActivityScenario.launch(StartViewActivity.class);
        assertNotNull(scenario);
        scenario.close();
    }
    @Test
    public void testGoingFromStartToIsotopeActivity() {
        ActivityScenario<StartViewActivity> scenario = ActivityScenario.launch(StartViewActivity.class);
        onView(withId(R.id.button_select_isotope)).perform(closeSoftKeyboard());
        onView(withId(R.id.button_select_isotope))
                .perform(click());
        onView(withId(R.id.isotope_menu_view))
                .check(matches(isDisplayed()));
        scenario.close();
    }
    @Test
    public void testGoingFromStartToXrayActivity() {
        ActivityScenario<StartViewActivity> scenario = ActivityScenario.launch(StartViewActivity.class);
        onView(withId(R.id.button_select_xray)).perform(closeSoftKeyboard());
        onView(withId(R.id.button_select_xray))
                .perform(click());
        onView(withId(R.id.x_ray_menu_view))
                .check(matches(isDisplayed()));
        scenario.close();
    }
    @Test
    public void testGoingFromXRayToCalculatedActivityWithInvalidData() {
        ActivityScenario<XRayMenuViewActivity> scenario = ActivityScenario.launch(XRayMenuViewActivity.class);
        /*Mandatory*/
        scenario.onActivity(StringGetter::setAppContext);

        onView(withId(R.id.calculate_button_xray)).perform(closeSoftKeyboard());
        onView(withId(R.id.calculate_button_xray))
                .perform(click());
        /*
         * Check if instead of calculated view we see error dialog and click "OK"
         */
        onView(withText(ERROR_BUTTON_TEXT))
                .check(matches(isDisplayed()))
                .perform(click());
        /*
         * Check if we went back to original activity
         *
        onView(withId(R.id.x_ray_menu_view))
                .check(matches(isDisplayed()));
        */
        scenario.close();
    }
    @Test
    public void testGoingFromXRayToCalculatedActivityWithValidData() {
        ActivityScenario<XRayMenuViewActivity> scenario = ActivityScenario.launch(XRayMenuViewActivity.class);
        /*Mandatory*/
        scenario.onActivity(StringGetter::setAppContext);
        /*Simple mock data which produces valid time*/
        final int[] components =
                {
                    R.id.inputXRayCurrent,
                    R.id.inputXRaySourceToDetector,
                    R.id.inputXRaySteelThickness,
                    R.id.inputXRayTargetDensity
                };
        for (int component : components) {
            onView(withId(component))
                    .perform(typeText("4"), closeSoftKeyboard())
                    .check(matches(withText("4")));
        }
        /*Actual test*/
        onView(withId(R.id.calculate_button_xray)).perform(closeSoftKeyboard());
        onView(withId(R.id.calculate_button_xray))
                .perform(click());
        onView(withId(R.id.calculated_view)).check(matches(isDisplayed()));
        scenario.close();
    }
}
