package com.julian.lbniwkalkulator.enums;

public enum IsotopeTypeEnum {
    _SE_75("SE_75"),
    _IR_192("IR_192"),
    _CO_60("CO_60");

    private final String isotopeName;

    IsotopeTypeEnum(String isotopeName) {
        this.isotopeName = isotopeName;
    }
}
