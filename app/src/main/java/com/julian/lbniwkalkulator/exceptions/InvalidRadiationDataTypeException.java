package com.julian.lbniwkalkulator.exceptions;

public class InvalidRadiationDataTypeException extends Exception{
    private final String invalidType;

    public InvalidRadiationDataTypeException(String message, String invalidType) {
        super(message);
        this.invalidType = invalidType;
    }

    public String getInvalidType() {
        return invalidType;
    }
}
