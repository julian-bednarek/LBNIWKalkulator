package com.julian.lbniwkalkulator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.julian.lbniwkalkulator.pages.StartViewActivity;
import com.julian.lbniwkalkulator.util.ErrorHandler;
import com.julian.lbniwkalkulator.util.StringGetter;

import org.junit.Test;
import org.junit.runner.RunWith;

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
        ActivityScenario<StartViewActivity> scenario = ActivityScenario.launch(StartViewActivity.class);
        scenario.onActivity(StringGetter::setAppContext);
        Runnable dismissAction = () -> Log.d("Something", "Something");
        String exceptionMessage = "Mock exception message";
        int additionalMessage = R.string.decimal_placeholder;
        Object object = 4.0;

        scenario.onActivity(activity ->
                ErrorHandler.processException(activity, exceptionMessage, additionalMessage, object, dismissAction));
        onView(withText(ErrorHandler.ERROR_BUTTON_TEXT))
                .check(matches(isDisplayed()));

        scenario.close();
    }
    @Test
    public void testBasicInvocationOfProcessExceptionsWithNullArguments() {
        ActivityScenario<StartViewActivity> scenario = ActivityScenario.launch(StartViewActivity.class);
        scenario.onActivity(StringGetter::setAppContext);
        String exceptionMessage = "Mock exception message";

        scenario.onActivity(activity ->
                ErrorHandler.processException(activity, exceptionMessage, null, null, null));
        onView(withText(ErrorHandler.ERROR_BUTTON_TEXT))
                .check(matches(isDisplayed()));

        scenario.close();
    }
}
