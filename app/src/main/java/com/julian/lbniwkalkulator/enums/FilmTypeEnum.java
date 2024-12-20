package com.julian.lbniwkalkulator.enums;

import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

public enum FilmTypeEnum {
    _D1(1),
    _D2(2),
    _D3(3),
    _D4(4),
    _D5(5),
    _D6(6),
    _D7(7);


    private int typeNumber;
    private static String STRINGIFIED_ENUM_REGEX = " D[0-9]";

    FilmTypeEnum(int typeNumber) {
        this.typeNumber = typeNumber;
    }

    public int getTypeNumber() {
        return typeNumber;
    }

    public static int valueFromString(String stringifiedEnum) throws InputNotSupportedException {
        if (stringifiedEnum.matches(STRINGIFIED_ENUM_REGEX)) {
            char digit = stringifiedEnum.charAt(stringifiedEnum.length() - 1);
            return Character.getNumericValue(digit);
        }
        throw new InputNotSupportedException("Parsing value from film enum failed");
    }
}
