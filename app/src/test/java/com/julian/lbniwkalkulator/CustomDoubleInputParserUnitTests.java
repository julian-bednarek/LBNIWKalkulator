package com.julian.lbniwkalkulator;

import org.junit.Test;
import static org.junit.Assert.*;
import static com.julian.lbniwkalkulator.calculations.CustomInputParsers.*;

import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

public class CustomDoubleInputParserUnitTests {
    private final static double PRECISION = 1e-12;

    @Test
    public void testEmptyInputDoubleParser() {
        try {
            assertEquals(parseInputDouble(null), 0, PRECISION);
            assertEquals(parseInputDouble(""), 0, PRECISION);
        } catch (Exception ignored) {}
    }
    @Test
    public void testInvalidInputDoubleParser() {
        try {
            parseInputDouble("-0.0");
            fail("Expected InputNotSupportedException at input: -0.0");
        } catch (InputNotSupportedException ignored) {}

        try {
            parseInputDouble("a0.0");
            fail("Expected InputNotSupportedException at input: a0.0");
        } catch (InputNotSupportedException ignored) {}

        try {
            parseInputDouble("-0");
            fail("Expected InputNotSupportedException at input: -0");
        } catch (InputNotSupportedException ignored) {}

        try {
            parseInputDouble("123.");
            fail("Expected InputNotSupportedException at input: 123.");
        } catch (InputNotSupportedException ignored) {}

        try {
            parseInputDouble("0.0a");
            fail("Expected InputNotSupportedException at input: 0.0a");
        } catch (InputNotSupportedException ignored) {}
    }
    @Test
    public void testValidInputDoubleParser() {
        try {
            assertEquals(parseInputDouble("123.45"), 123.45, PRECISION);
            assertEquals(parseInputDouble("123"), 123, PRECISION);
        } catch (Exception ignored) {}
    }
}
