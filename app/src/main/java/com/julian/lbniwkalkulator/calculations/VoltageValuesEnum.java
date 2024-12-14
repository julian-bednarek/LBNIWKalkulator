package com.julian.lbniwkalkulator.calculations;

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

    VoltageValuesEnum(int voltage) {
        this.voltage = voltage;
    }

    public int getVoltage() {
        return voltage;
    }
}
