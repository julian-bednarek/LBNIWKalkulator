package com.julian.lbniwkalkulator.enums;

import android.util.Log;

import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

public enum VoltageValuesEnum {
    _50kV(50_000),
    _60kV(60_000),
    _140kV(140_000),
    _150kV(150_000),
    _170kV(170_000),
    _180kV(180_000),
    _200kV(200_000),
    _230kV(230_000),
    _320kV(320_000);

    private final int voltage;
    private static final String STRINGIFIED_ENUM_REGEX = " \\d{1,3}kV";

    VoltageValuesEnum(int voltage) {
        this.voltage = voltage;
    }

    public int getVoltage() {
        return voltage;
    }

    /**
     * Parses a stringified voltage enum to extract the voltage value.
     *
     * @param stringifiedEnum the input string in the format "XkV" where X is the voltage.
     * @return the parsed voltage value as an integer.
     * @throws InputNotSupportedException if the input format is invalid.
     */
    public static int valueFromString(String stringifiedEnum) throws InputNotSupportedException {
        if (stringifiedEnum == null || stringifiedEnum.isEmpty()) {
            throw new InputNotSupportedException("Input string cannot be null or empty");
        }
        Log.d("KURWA", stringifiedEnum);
        if (stringifiedEnum.matches(STRINGIFIED_ENUM_REGEX)) {
            String voltageString = stringifiedEnum.substring(1, stringifiedEnum.length() - 2);
            return Integer.parseInt(voltageString);
        }
        throw new InputNotSupportedException("Parsing value from voltage enum failed: Invalid format");
    }
}
