package com.julian.lbniwkalkulator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.julian.lbniwkalkulator.util.ErrorHandler.ERROR_BUTTON_TEXT;

import android.view.View;
import android.widget.EditText;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.fragment.app.testing.FragmentScenario;
import com.julian.lbniwkalkulator.components.inputfields.InputFieldWrapper;
import com.julian.lbniwkalkulator.fragments.StartViewFragment;
import com.julian.lbniwkalkulator.fragments.XRayMenuViewFragment;
import com.julian.lbniwkalkulator.util.StringGetter;

import org.hamcrest.Matcher;
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
        FragmentScenario<StartViewFragment> scenario = FragmentScenario.launchInContainer(StartViewFragment.class);
        assertNotNull(scenario);
        scenario.close();
    }
    @Test
    public void testGoingFromStartToIsotopeActivity() {
        FragmentScenario<StartViewFragment> scenario = FragmentScenario.launchInContainer(StartViewFragment.class);
        onView(withId(R.id.button_select_isotope)).perform(closeSoftKeyboard());
        onView(withId(R.id.button_select_isotope))
                .perform(click());
        onView(withId(R.id.isotope_menu_view))
                .check(matches(isDisplayed()));
        scenario.close();
    }
    @Test
    public void testGoingFromStartToXrayActivity() {
        FragmentScenario<StartViewFragment> scenario = FragmentScenario.launchInContainer(StartViewFragment.class);
        onView(withId(R.id.button_select_xray)).perform(closeSoftKeyboard());
        onView(withId(R.id.button_select_xray))
                .perform(click());
        onView(withId(R.id.x_ray_menu_view))
                .check(matches(isDisplayed()));
        scenario.close();
    }
    @Test
    public void testGoingFromXRayToCalculatedActivityWithInvalidData() {
        FragmentScenario<XRayMenuViewFragment> scenario = FragmentScenario.launchInContainer(XRayMenuViewFragment.class);
        /*Mandatory*/
        scenario.onFragment(fragment -> StringGetter.setAppContext(fragment.getContext()));
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
         */
        onView(withId(R.id.x_ray_menu_view))
                .check(matches(isDisplayed()));
        scenario.close();
    }
    @Test
    public void testGoingFromXRayToCalculatedActivityWithValidData() {
        FragmentScenario<XRayMenuViewFragment> scenario = FragmentScenario.launchInContainer(XRayMenuViewFragment.class);
        /*Mandatory*/
        scenario.onFragment(fragment -> StringGetter.setAppContext(fragment.getContext()));
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
                    .perform(new ViewAction() {
                        @Override
                        public Matcher<View> getConstraints() {
                            return isAssignableFrom(InputFieldWrapper.class);
                        }

                        @Override
                        public String getDescription() {
                            return "Typing text in wrapper edit text";
                        }

                        @Override
                        public void perform(UiController uiController, View view) {
                            EditText editText = ((InputFieldWrapper) view).getText_input().getInput();
                            editText.setText("4");
                        }
                    }, closeSoftKeyboard());
        }
        /*Actual test*/
        onView(withId(R.id.calculate_button_xray)).perform(closeSoftKeyboard());
        onView(withId(R.id.calculate_button_xray))
                .perform(click());
        onView(withId(R.id.calculated_view)).check(matches(isDisplayed()));
        scenario.close();
    }
}
