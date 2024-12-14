package com.julian.lbniwkalkulator.exceptions;

public class MissingComponentParameterException extends InvalidComponentException{
    private final String missingParameter;

    public MissingComponentParameterException(String message, String missingParameter) {
        super(message);
        this.missingParameter = missingParameter;
    }

    public String getMissingParameter() {
        return missingParameter;
    }
}
