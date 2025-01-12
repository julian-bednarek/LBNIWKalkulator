package com.julian.lbniwkalkulator.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class for most popular isotopes that can be used if we pick 'other'
 */
public enum IsotopeTypeEnum implements InputEnumScheme {
    /**
     * Approximately 119.78 days
     */
    _Se_75(10_349_280),
    /**
     * Approximately 73.827 days
     */
    _Ir_192(6_378_652.8);

    /**
     * In seconds
     */
    private final double halfLife;

    IsotopeTypeEnum(double halfLife) {
        this.halfLife = halfLife;
    }

    public double getHalfLife() {
        return halfLife;
    }

    public double getHalfLifeInDays() {
        return halfLife / (60 * 60 * 24);
    }

    @Override
    public List<String> getContents() {
        return Stream.of(values()).map(IsotopeTypeEnum::getParsedName).collect(Collectors.toList());
    }

    @Override
    public String getParsedName() {
        String[] parts = name().split("_");
        return parts[1] + "-" + parts[2];
    }
}
