package com.julian.lbniwkalkulator.calculations;

import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.calculations.dataclasess.IsotopeData;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationDataTypes;
import com.julian.lbniwkalkulator.calculations.dataclasess.XRayData;

public class RadiationDataProcessor {
    public static ExposureTime processRadiationData(RadiationData data) {
        switch (data.getType()) {
            case RadiationDataTypes.ISOTOPE:
                return processIsotopeData((IsotopeData) data);
            case RadiationDataTypes.XRAY:
                return processXRayData((XRayData) data);
        }
        return null;
    }

    private static ExposureTime processXRayData(XRayData data) {
        XRayExposureTimeCalculator calculator = new XRayExposureTimeCalculator(data);
        return calculator.getTotalExposureTime();
    }

    private static ExposureTime processIsotopeData(IsotopeData data) {
        IsotopeExposureTimeCalculator calculator = new IsotopeExposureTimeCalculator(data);
        return calculator.getTotalExposureTime();
    }
}
