package com.julian.lbniwkalkulator.calculations;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.calculations.dataclasess.XRayData;
import com.julian.lbniwkalkulator.exceptions.ExposureTimeTooLongException;
import com.julian.lbniwkalkulator.util.StringGetter;

public class XRayExposureTimeCalculator implements CalculationScheme {

    private final XRayData data;

    @Override
    public ExposureTime getTotalExposureTime() throws ExposureTimeTooLongException {
        return computeExposureTime();
    }

    public XRayExposureTimeCalculator(XRayData data) {
        this.data = data;
    }

    private ExposureTime computeExposureTime() throws ExposureTimeTooLongException {
        int power = computePower(data);
        int filmType = data.getFilmType();
        double targetDensity = data.getTargetDensity();
        double targetThickness = data.getSteelThickness();
        double sourceToDetectorDistance = data.getSourceToDetectorDistance();
        int exposureTimeInSeconds = (int) (Math.exp(targetDensity * targetThickness) /
                (power * Math.pow(sourceToDetectorDistance, 2)));
        ExposureTime retval = new ExposureTime(
                exposureTimeInSeconds / SECONDS_IN_MINUTE,
                exposureTimeInSeconds % SECONDS_IN_MINUTE);
        if(retval.getMinutes() > 99) throw new ExposureTimeTooLongException(
                StringGetter.fromStringsXML(R.string.exception_too_long_exposure_time), retval);
        return retval;
    }

    /**
     * Function uses regular formula for DC power which is P = I * V
     */
    private int computePower(XRayData data) {
        return data.getCurrent() * data.getVoltage();
    }
}
