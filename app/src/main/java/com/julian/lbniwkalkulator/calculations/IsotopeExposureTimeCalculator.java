package com.julian.lbniwkalkulator.calculations;

import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.calculations.dataclasess.IsotopeData;

public class IsotopeExposureTimeCalculator implements CalculationScheme{
    private final IsotopeData data;

    @Override
    public ExposureTime getTotalExposureTime() {
        return null;
    }

    public IsotopeExposureTimeCalculator(IsotopeData data) {
        this.data = data;
    }
}
