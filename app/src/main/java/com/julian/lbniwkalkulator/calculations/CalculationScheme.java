package com.julian.lbniwkalkulator.calculations;

import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;

public interface CalculationScheme {
    int SECONDS_IN_MINUTE = 60;
    ExposureTime getTotalExposureTime();
}
