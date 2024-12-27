package com.julian.lbniwkalkulator.enums;

public enum IsotopeTypeEnum {
    _SE_75(0),
    _IR_192(1),
    _CO_60(2);

    private final double halfLife;

    IsotopeTypeEnum(double halfLife) {
        this.halfLife = halfLife;
    }
}
