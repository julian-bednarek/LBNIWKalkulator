package com.julian.lbniwkalkulator.exceptions;

import com.julian.lbniwkalkulator.dataclasess.ExposureTime;

public class ExposureTimeTooLongException extends Exception{
    private final ExposureTime exposureTime;

    public ExposureTimeTooLongException(String message, ExposureTime time) {
        super(message);
        this.exposureTime = time;
    }

    public ExposureTime getExposureTime() {
        return exposureTime;
    }
}
