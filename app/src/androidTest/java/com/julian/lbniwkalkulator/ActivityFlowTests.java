package com.julian.lbniwkalkulator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.julian.lbniwkalkulator.util.ErrorHandler.ERROR_BUTTON_TEXT;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import com.julian.lbniwkalkulator.pages.StartViewActivity;
import com.julian.lbniwkalkulator.pages.XRayMenuViewActivity;
import com.julian.lbniwkalkulator.util.StringGetter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testng.annotations.BeforeClass;

@RunWith(AndroidJUnit4.class)
public class ActivityFlowTests {
    @BeforeClass
    public static void disableAnimations() {
        try {
            UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            device.executeShellCommand("settings put global window_animation_scale 0");
            device.executeShellCommand("settings put global transition_animation_scale 0");
            device.executeShellCommand("settings put global animator_duration_scale 0");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        // TODO: Fix
        // Issue lies in fact that actual edit text is inside input field wrapper
        assert false;
        onView(withId(R.id.inputXRayCurrent)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.inputXRaySourceToDetector)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.inputXRayTargetDensity)).perform(typeText("7"), closeSoftKeyboard());
        onView(withId(R.id.inputXRaySteelThickness)).perform(typeText("1"), closeSoftKeyboard());
        /*Actual test*/
        onView(withId(R.id.calculate_button_xray)).perform(closeSoftKeyboard());
        onView(withId(R.id.calculate_button_xray))
                .perform(click());
        onView(withId(R.id.calculated_view)).check(matches(isDisplayed()));
        scenario.close();
    }
}
