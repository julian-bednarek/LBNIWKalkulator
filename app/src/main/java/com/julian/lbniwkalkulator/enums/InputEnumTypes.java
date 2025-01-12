package com.julian.lbniwkalkulator.enums;

import com.julian.lbniwkalkulator.exceptions.InvalidComponentParameterException;

import java.util.List;
import java.util.Objects;

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

    public List<String> getContentsFromEnum() {
        try {
            return ((InputEnumScheme) Objects.requireNonNull(enumClass.getEnumConstants())[0])
                    .getContents();
        } catch (Exception e) {
            throw new RuntimeException("Bruh bruh bruh", e);
        }
    }

    public List<String> getContentsFromEnumWithRange(int range) {
        try {
            return ((InputEnumScheme) Objects.requireNonNull(enumClass.getEnumConstants())[0])
                    .getContents(range);
        } catch (Exception e) {
            throw new RuntimeException("Bruh bruh bruh", e);
        }
    }
}
