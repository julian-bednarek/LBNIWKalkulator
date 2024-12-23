package com.julian.lbniwkalkulator.calculations;

import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.calculations.dataclasess.XRayData;

public class XRayExposureTimeCalculator implements CalculationScheme{

    private final XRayData data;

    @Override
    public ExposureTime getTotalExposureTime() {
        return computeExposureTime();
    }

    public XRayExposureTimeCalculator(XRayData data) {
        this.data = data;
    }

    private ExposureTime computeExposureTime() {
        int power = computePower(data);
        int filmType = data.getFilmType();
        double targetDensity = data.getTargetDensity();
        double targetThickness = data.getSteelThickness();
        double sourceToDetectorDistance = data.getSourceToDetectorDistance();
        int exposureTimeInSeconds = (int) (Math.exp(targetDensity * targetThickness) /
                (power * Math.pow(sourceToDetectorDistance, 2)));
        return new ExposureTime(exposureTimeInSeconds / SECONDS_IN_MINUTE,
                                exposureTimeInSeconds % SECONDS_IN_MINUTE);
    }

    /**
     * Function uses regular formula for DC power which is P = I * V
     */
    private int computePower(XRayData data) {
        return data.getCurrent() * data.getVoltage();
    }
}
