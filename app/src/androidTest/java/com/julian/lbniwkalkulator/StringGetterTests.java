package com.julian.lbniwkalkulator;

import static org.junit.Assert.*;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.julian.lbniwkalkulator.util.StringGetter;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Class requires application context to run test,
 * that's why this simple class is in android test directory
 */

@RunWith(AndroidJUnit4.class)
public class StringGetterTests {
    @Test
    public void testThrowingException() {
        assertThrows(NullPointerException.class, () -> StringGetter.fromStringsXML(R.string.exception_too_long_exposure_time_additional));
    }
    @Test
    public void testValidData() {
        StringGetter.setAppContext(ApplicationProvider.getApplicationContext());
        assertEquals("0.0",StringGetter.fromStringsXML(R.string.decimal_placeholder));
        assertEquals("0", StringGetter.fromStringsXML(R.string.integer_placeholder));
    }
}
