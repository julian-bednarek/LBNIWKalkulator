package com.julian.lbniwkalkulator.enums;

import java.util.List;

public interface InputEnumScheme {
    default List<String> getContents() throws Exception {
        throw new UnsupportedOperationException("This method must be implemented if 'getContents(int range)' is not.");
    }
    default List<String> getContents(int range) throws Exception {
        throw new UnsupportedOperationException("This method must be implemented if 'getContents()' is not.");
    }

    String getParsedName();
}
