package com.julian.lbniwkalkulator.enums;

import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum FilmTypeEnum implements InputEnumScheme {
    _D1(1),
    _D2(2),
    _D3(3),
    _D4(4),
    _D5(5),
    _D6(6),
    _D7(7);


    private final int typeNumber;
    private static final String STRINGIFIED_ENUM_REGEX = "D[0-9]";

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

    @Override
    public String getParsedName() {
        return name().substring(1);
    }

    public List<String> getContents() {
        return Stream.of(values())
                .map(FilmTypeEnum::getParsedName)
                .collect(Collectors.toList());
    }

}
