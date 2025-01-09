package com.julian.lbniwkalkulator.enums;

public enum TableColumns {
    ISOTOPE(0),
    CURRENT_ACTIVITY(1);
    private final int index;

    TableColumns(int index) {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }
}

