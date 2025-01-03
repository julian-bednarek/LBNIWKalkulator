package com.julian.lbniwkalkulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

import org.junit.Test;

public class ExposureTimeClassTests {
    @Test
    public void testFromMilliseconds() {
        try {
            long timeInMsForValue1 = 21_376;
            ExposureTime testValue1 = ExposureTime.fromMilliseconds(timeInMsForValue1);
            assertEquals("00:21.37", testValue1.toString());
            ExposureTime testValue2 = ExposureTime.fromMilliseconds(0);
            assertEquals("00:00.00", testValue2.toString());
            ExposureTime testValue3 = ExposureTime.fromMilliseconds(3);
            assertEquals("00:00.00", testValue3.toString());
            long timeInMsForValue4 = 216_115;
            ExposureTime testValue4 = ExposureTime.fromMilliseconds(timeInMsForValue4);
            assertEquals("03:36.11", testValue4.toString());
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
        assertThrows(InputNotSupportedException.class, () -> ExposureTime.fromMilliseconds(-1_000));

    }
    @Test
    public void testToMilliseconds() {
        try {
            ExposureTime testValue1 = ExposureTime.fromMilliseconds(303_990);
            assertEquals(303_990, testValue1.toMilliseconds());

            ExposureTime testValue2 = ExposureTime.fromMilliseconds(0);
            assertEquals(0, testValue2.toMilliseconds());

            ExposureTime testValue3 = ExposureTime.fromMilliseconds(10);
            assertEquals(10, testValue3.toMilliseconds());

            ExposureTime testValue4 = ExposureTime.fromMilliseconds(45_000);
            assertEquals(45_000, testValue4.toMilliseconds());

            ExposureTime testValue5 = ExposureTime.fromMilliseconds(180_000);
            assertEquals(180_000, testValue5.toMilliseconds());

            ExposureTime testValue6 = ExposureTime.fromMilliseconds(135_250);
            assertEquals(135_250, testValue6.toMilliseconds());

            ExposureTime testValue7 = ExposureTime.fromMilliseconds(5_999_990);
            assertEquals(5_999_990, testValue7.toMilliseconds());
        } catch (InputNotSupportedException e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }
    @Test
    public void testConstructor() {
        assertThrows(InputNotSupportedException.class, () -> ExposureTime.fromMilliseconds(-1_000));
        assertThrows(InputNotSupportedException.class, () -> ExposureTime.fromMilliseconds(Long.MIN_VALUE));
    }

}
