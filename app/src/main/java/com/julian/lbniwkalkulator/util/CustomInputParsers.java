package com.julian.lbniwkalkulator.util;

import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

public class CustomInputParsers {
    private static final String INT_REGEX = "^[0-9]+$";
    private static final String DOUBLE_REGEX = "^[0-9]+(\\.[0-9]+)?$";

    public static int parseInputInt(String input) throws InputNotSupportedException {
        if(input == null || input.isEmpty()) {
            return 0;
        } else if (input.matches(INT_REGEX)) {
            return Integer.parseInt(input);
        } else {
            throw new InputNotSupportedException("You provided invalid integer");
        }
    }

    public static double parseInputDouble(String input) throws InputNotSupportedException {
        if(input == null || input.isEmpty()) {
            return 0.0;
        } else if (input.matches(DOUBLE_REGEX)) {
            return Double.parseDouble(input);
        } else {
            throw new InputNotSupportedException("You provided invalid double");
        }
    }
}
