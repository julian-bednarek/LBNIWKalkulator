package com.julian.lbniwkalkulator.calculations;

import com.julian.lbniwkalkulator.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.dataclasess.IsotopeData;

public class IsotopeExposureTimeCalculator implements CalculationScheme{
    private final IsotopeData data;

    @Override
    public ExposureTime getTotalExposureTime() {
        return new ExposureTime();
    }

    public IsotopeExposureTimeCalculator(IsotopeData data) {
        this.data = data;
    }
}
