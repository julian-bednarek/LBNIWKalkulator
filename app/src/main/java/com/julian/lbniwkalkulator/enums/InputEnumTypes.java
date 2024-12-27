package com.julian.lbniwkalkulator.enums;

public enum InputEnumTypes {
    FILM_TYPE(FilmTypeEnum.class),
    VOLTAGE_VALUE(VoltageValuesEnum.class),
    ISOTOPE_ENUM(IsotopeTypeEnum.class);

    private final Class<? extends Enum<?>> enumClass;

    InputEnumTypes(Class<? extends Enum<?>> enumClass) {
        this.enumClass = enumClass;
    }

    public Class<? extends Enum<?>> getEnumClass() {
        return enumClass;
    }
}
