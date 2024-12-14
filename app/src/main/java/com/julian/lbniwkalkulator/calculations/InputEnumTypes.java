package com.julian.lbniwkalkulator.calculations;

public enum InputEnumTypes {
    FILM_TYPE(FilmTypeEnum.class),
    VOLTAGE_VALUE(VoltageValuesEnum.class);

    private final Class<? extends Enum<?>> enumClass;

    InputEnumTypes(Class<? extends Enum<?>> enumClass) {
        this.enumClass = enumClass;
    }

    public Class<? extends Enum<?>> getEnumClass() {
        return enumClass;
    }
}
