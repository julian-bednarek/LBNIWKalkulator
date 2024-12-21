package com.julian.lbniwkalkulator.calculations;

import java.math.MathContext;

public class XRayExposureTimeCalculator implements CalculationScheme{
    private static final int SECONDS_IN_MINUTE = 60;

    @Override
    public ExposureTime getTotalExposureTime(XRayData data) {
        return computeExposureTime(data);
    }

    private ExposureTime computeExposureTime(XRayData data) {
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
