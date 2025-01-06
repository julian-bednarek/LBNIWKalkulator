package com.julian.lbniwkalkulator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.julian.lbniwkalkulator.util.ErrorHandler.ERROR_BUTTON_TEXT;
import static org.junit.Assert.assertEquals;
import android.util.Log;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.julian.lbniwkalkulator.fragments.StartViewFragment;
import com.julian.lbniwkalkulator.util.ErrorHandler;
import com.julian.lbniwkalkulator.util.StringGetter;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * I don't know why those test doesn't work
 * In ActivityFlowTest everything is fine
 */
@RunWith(AndroidJUnit4.class)
public class ErrorHandlerTests {

    @Test
    public void testGetActualCauseSingleLevelException() {
        Exception exception = new Exception("Top level exception");
        Throwable actualCause = ErrorHandler.getActualCause(exception);
        assertEquals(exception, actualCause);
    }

    @Test
    public void testGetActualCauseNestedException() {
        Exception rootCause = new Exception("Root cause");
        Exception intermediateCause = new Exception("Intermediate cause", rootCause);
        Exception topLevelException = new Exception("Top level exception", intermediateCause);

        Throwable actualCause = ErrorHandler.getActualCause(topLevelException);
        assertEquals(rootCause, actualCause);
    }
    @Test
    public void testBasicInvocationOfProcessException() {
        FragmentScenario<StartViewFragment> scenario = FragmentScenario.launchInContainer(StartViewFragment.class);
        scenario.onFragment(fragment -> StringGetter.setAppContext(fragment.getContext()));

        Runnable dismissAction = () -> Log.d("Something", "Something");
        String exceptionMessage = "Mock exception message";
        int additionalMessage = R.string.decimal_placeholder;
        Object object = 4.0;

        scenario.onFragment(fragment -> ErrorHandler.processException(fragment.getContext(), exceptionMessage, additionalMessage, object, dismissAction));

        onView(withText(ERROR_BUTTON_TEXT))
                .check(matches(isDisplayed()))
                .perform(click());

        scenario.close();
    }

    @Test
    public void testBasicInvocationOfProcessExceptionsWithNullArguments() {
        FragmentScenario<StartViewFragment> scenario = FragmentScenario.launchInContainer(StartViewFragment.class);
        scenario.onFragment(fragment -> StringGetter.setAppContext(fragment.getContext()));

        String exceptionMessage = "Mock exception message";

        scenario.onFragment(fragment -> ErrorHandler.processException(fragment.getContext(), exceptionMessage, null, null, null));

        onView(withText(ERROR_BUTTON_TEXT))
                .check(matches(isDisplayed()))
                .perform(click());

        scenario.close();
    }
}

