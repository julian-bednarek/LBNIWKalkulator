package com.julian.lbniwkalkulator.calculations;

import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.calculations.dataclasess.IsotopeData;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationDataTypes;
import com.julian.lbniwkalkulator.calculations.dataclasess.XRayData;
import com.julian.lbniwkalkulator.exceptions.ExposureTimeTooLongException;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.exceptions.InvalidRadiationDataTypeException;

public class RadiationDataProcessor {
    public static ExposureTime processRadiationData(RadiationData data) throws ExposureTimeTooLongException, InvalidRadiationDataTypeException, InputNotSupportedException {
        switch (data.getType()) {
            case RadiationDataTypes.ISOTOPE:
                return processIsotopeData((IsotopeData) data);
            case RadiationDataTypes.XRAY:
                return processXRayData((XRayData) data);
        }
        throw new InvalidRadiationDataTypeException("Radiation sub type is invalid", data.getType());
    }

    private static ExposureTime processXRayData(XRayData data) throws ExposureTimeTooLongException, InputNotSupportedException {
        XRayExposureTimeCalculator calculator = new XRayExposureTimeCalculator(data);
        return calculator.getTotalExposureTime();
    }

    private static ExposureTime processIsotopeData(IsotopeData data) {
        IsotopeExposureTimeCalculator calculator = new IsotopeExposureTimeCalculator(data);
        return calculator.getTotalExposureTime();
    }
}
