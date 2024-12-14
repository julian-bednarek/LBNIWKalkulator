package com.julian.lbniwkalkulator.exceptions;

public class InvalidComponentParameterException extends InvalidComponentException {
    private final String invalidParameter;

    public InvalidComponentParameterException(String message, String invalidParameter) {
        super(message);
        this.invalidParameter = invalidParameter;
    }

    public String getInvalidParameter() {
        return invalidParameter;
    }
}
