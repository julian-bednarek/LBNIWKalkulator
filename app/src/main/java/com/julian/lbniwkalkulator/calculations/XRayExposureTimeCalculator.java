package com.julian.lbniwkalkulator.calculations;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.dataclasess.XRayData;
import com.julian.lbniwkalkulator.exceptions.ExposureTimeTooLongException;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.util.StringGetter;

public class XRayExposureTimeCalculator implements CalculationScheme {

    private static final int MAX_EXPOSURE_TIME_MINUTES = 99;

    private final XRayData data;

    @Override
    public ExposureTime getTotalExposureTime() throws ExposureTimeTooLongException, InputNotSupportedException {
        return computeExposureTime();
    }

    public XRayExposureTimeCalculator(XRayData data) {
        this.data = data;
    }

    private ExposureTime computeExposureTime() throws ExposureTimeTooLongException, InputNotSupportedException {
        int power = computePower(data);
        int filmType = data.getFilmType();
        double targetDensity = data.getTargetDensity();
        double targetThickness = data.getSteelThickness();
        double sourceToDetectorDistance = data.getSourceToDetectorDistance();
        if(power == 0 || sourceToDetectorDistance == 0) throw new InputNotSupportedException(StringGetter.fromStringsXML(R.string.exception_input_not_supported_div_by_0));
        int exposureTimeInSeconds = (int) (Math.exp(targetDensity * targetThickness) /
                (power * Math.pow(sourceToDetectorDistance, 2)));
        ExposureTime retval = ExposureTime.fromMilliseconds(exposureTimeInSeconds);
        if(retval.getMinutes() > MAX_EXPOSURE_TIME_MINUTES) throw new ExposureTimeTooLongException(
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
