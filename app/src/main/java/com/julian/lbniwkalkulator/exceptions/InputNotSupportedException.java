package com.julian.lbniwkalkulator.exceptions;

public class InputNotSupportedException extends Exception {
    private final Object invalidInput;

    public InputNotSupportedException(String message, Object invalidInput) {
        super(message);
        this.invalidInput = invalidInput;
    }

    public InputNotSupportedException(String message) {
        super(message);
        invalidInput = null;
    }

    public Object getInvalidInput() {
        return invalidInput;
    }
}
