package com.julian.lbniwkalkulator.calculations;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.dataclasess.IsotopeData;
import com.julian.lbniwkalkulator.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.dataclasess.RadiationDataTypes;
import com.julian.lbniwkalkulator.dataclasess.XRayData;
import com.julian.lbniwkalkulator.exceptions.ExposureTimeTooLongException;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.exceptions.InvalidRadiationDataTypeException;
import com.julian.lbniwkalkulator.util.StringGetter;


public class RadiationDataProcessor {
    /**
     * Strategy pattern implementation for calculating the correct exposure time.
     * <p>
     * This class safely handles different types of radiation data. If a new radiation data type
     * has not been implemented, an {@link InvalidRadiationDataTypeException} will be thrown.
     * </p>
     */
    public static ExposureTime processRadiationData(RadiationData data) throws ExposureTimeTooLongException, InvalidRadiationDataTypeException, InputNotSupportedException {
        return switch (data.getType()) {
            case RadiationDataTypes.ISOTOPE -> processIsotopeData((IsotopeData) data);
            case RadiationDataTypes.XRAY -> processXRayData((XRayData) data);
            default -> throw new InvalidRadiationDataTypeException(
                    StringGetter.fromStringsXML(R.string.exception_invalid_radiation_data_type),
                    data.getType());
        };
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
