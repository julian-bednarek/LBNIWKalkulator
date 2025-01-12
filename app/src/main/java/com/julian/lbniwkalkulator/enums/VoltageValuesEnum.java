package com.julian.lbniwkalkulator.enums;

import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

import java.util.ArrayList;
import java.util.List;

public enum VoltageValuesEnum implements InputEnumScheme {
    _50kV(50_000),
    _60kV(60_000),
    _70kV(70_000),
    _80kV(80_000),
    _90kV(90_000),
    _100kV(100_000),
    _110kV(110_000),
    _120kV(120_000),
    _130kV(130_000),
    _140kV(140_000),
    _150kV(150_000),
    _160kV(160_000),
    _170kV(170_000),
    _180kV(180_000),
    _190kV(190_000),
    _200kV(200_000),
    _210kV(210_000),
    _220kV(220_000),
    _230kV(230_000),
    _240kV(240_000),
    _250kV(250_000),
    _260kV(260_000),
    _270kV(270_000),
    _280kV(280_000),
    _290kV(290_000),
    _300kV(300_000);

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
     * There is no need to multiply value by 1000 because current is given in mA so kV * mA = W
     * @param stringifiedEnum the input string in the format "XkV" where X is the voltage.
     * @return the parsed voltage value as an integer.
     * @throws InputNotSupportedException if the input format is invalid.
     */
    public static int valueFromString(String stringifiedEnum) throws InputNotSupportedException {
        if (stringifiedEnum == null || stringifiedEnum.isEmpty()) {
            throw new InputNotSupportedException("Input string cannot be null or empty");
        }
        if (stringifiedEnum.matches(STRINGIFIED_ENUM_REGEX)) {
            String voltageString = stringifiedEnum.substring(1, stringifiedEnum.length() - 2);
            return Integer.parseInt(voltageString);
        }
        throw new InputNotSupportedException("Parsing value from voltage enum failed: Invalid format");
    }

    public static final int RANGE_ERESCO = 200_000;
    public static final int RANGE_Y_SMART = 300_000;



    public List<String> getContents(int range) throws InputNotSupportedException {
        List<String> voltageValues = new ArrayList<>();
        for (VoltageValuesEnum voltage : VoltageValuesEnum.values()) {
            if (voltage.getVoltage() <= range) {
                voltageValues.add(voltage.getParsedName());
            }
        }
        if (voltageValues.isEmpty()) {
            throw new InputNotSupportedException("No voltage values found within the specified range");
        }
        return voltageValues;
    }

    @Override
    public String getParsedName() {
        return name().substring(1);
    }
}
