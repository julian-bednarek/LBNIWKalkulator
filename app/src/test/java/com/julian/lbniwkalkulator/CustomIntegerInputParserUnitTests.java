package com.julian.lbniwkalkulator;

import org.junit.Test;
import static org.junit.Assert.*;
import static com.julian.lbniwkalkulator.calculations.CustomInputParsers.*;

import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

public class CustomIntegerInputParserUnitTests {
    @Test
    public void testEmptyInputIntegerParser() {
        try {
            assertEquals(parseInputInt(null), 0);
            assertEquals(parseInputInt(""), 0);
        } catch (Exception ignored) {}
    }
    @Test
    public void testInvalidInputIntegerParser() {
        try {
            parseInputInt("a0");
            fail("Expected InputNotSupportedException at input: a0");
        } catch (InputNotSupportedException ignored) {}

        try {
            parseInputInt("0a");
            fail("Expected InputNotSupportedException at input: 0a");
        } catch (InputNotSupportedException ignored) {}

        try {
            parseInputInt("-0");
            fail("Expected InputNotSupportedException at input: -0");
        } catch (InputNotSupportedException ignored) {}

        try {
            parseInputInt("-1");
            fail("Expected InputNotSupportedException at input: -1");
        } catch (InputNotSupportedException ignored) {}

        try {
            parseInputInt("1.23");
            fail("Expected InputNotSupportedException at input: 1.23");
        } catch (InputNotSupportedException ignored) {}
    }
    @Test
    public void testValidInputIntegerParser() {
        try {
            assertEquals(parseInputInt("123"), 123);
            assertEquals(parseInputInt("0"), 0);
        } catch (Exception ignored) {}
    }
}
