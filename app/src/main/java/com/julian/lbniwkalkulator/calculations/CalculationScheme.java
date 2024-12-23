package com.julian.lbniwkalkulator.calculations;

import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.exceptions.ExposureTimeTooLongException;

public interface CalculationScheme {
    int SECONDS_IN_MINUTE = 60;
    ExposureTime getTotalExposureTime() throws ExposureTimeTooLongException;
}
