package com.julian.lbniwkalkulator.calculations;

public enum FilmTypeEnum {
    _D1(1),
    _D2(2),
    _D3(3),
    _D4(4),
    _D5(5),
    _D6(6),
    _D7(7);


    private int typeNumber;

    FilmTypeEnum(int typeNumber) {
        this.typeNumber = typeNumber;
    }

    public int getTypeNumber() {
        return typeNumber;
    }
}
