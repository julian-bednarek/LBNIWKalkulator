package com.julian.lbniwkalkulator.enums;

/**
 * Class for most popular isotopes that can be used if we pick 'other'
 */
public enum IsotopeTypeEnum {
    /**
     * Approximately 119.78 days
     */
    _SE_75(10_349_280),
    /**
     * Approximately 73.827 days
     */
    _IR_192(6_378_652.8);

    /**
     * In seconds
     */
    private final double halfLife;

    IsotopeTypeEnum(double halfLife) {
        this.halfLife = halfLife;
    }
}
