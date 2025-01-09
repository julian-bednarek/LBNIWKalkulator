package com.julian.lbniwkalkulator.calculations;

import com.julian.lbniwkalkulator.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.exceptions.ExposureTimeTooLongException;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

/**
 * Interface defining scheme for classes that calculates exposure time. Implementing this class is obligatory to work within {@link RadiationDataProcessor} to ensure maintainability
 */
public interface CalculationScheme {
    int SECONDS_IN_MINUTE = 60;
    ExposureTime getTotalExposureTime() throws ExposureTimeTooLongException, InputNotSupportedException;
}
