package com.julian.lbniwkalkulator.fragments;

import static com.julian.lbniwkalkulator.util.CustomInputParsers.parseInputDouble;
import static com.julian.lbniwkalkulator.util.CustomInputParsers.parseInputInt;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.components.InputFieldWrapper;
import com.julian.lbniwkalkulator.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.dataclasess.XRayData;
import com.julian.lbniwkalkulator.enums.FilmTypeEnum;
import com.julian.lbniwkalkulator.enums.VoltageValuesEnum;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

public class XRayMenuViewFragment extends AbstractInputMenuFragment {

    public XRayMenuViewFragment() {
        super(R.layout.view_x_ray_menu_layout);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                             @Nullable Bundle savedInstanceState) {

        ((RadioButton) view.findViewById(R.id.lamp_y_smart)).setChecked(true);
        generalSetUp(view, R.id.calculate_button_xray);
    }

    @Override
    protected RadiationData collectData(View rootView) {
        try {
            int voltage = VoltageValuesEnum.valueFromString(((InputFieldWrapper) rootView
                    .findViewById(R.id.inputXRayVoltage))
                    .getInputValue());
            int current = parseInputInt(((InputFieldWrapper) rootView
                    .findViewById(R.id.inputXRayCurrent))
                    .getInputValue());
            double steelThickness = parseInputDouble(((InputFieldWrapper) rootView
                    .findViewById(R.id.inputXRaySteelThickness))
                    .getInputValue());
            int filmType = FilmTypeEnum.valueFromString(((InputFieldWrapper) rootView
                    .findViewById(R.id.inputXRayFilmType))
                    .getInputValue());
            int sourceToDetectorDistance = parseInputInt(((InputFieldWrapper) rootView
                    .findViewById(R.id.inputXRaySourceToDetector))
                    .getInputValue());
            double targetDensity = parseInputDouble(((InputFieldWrapper) rootView
                    .findViewById(R.id.inputXRayTargetDensity))
                    .getInputValue());
            return new XRayData(voltage, current, steelThickness,
                    filmType, sourceToDetectorDistance, targetDensity);
        } catch (InputNotSupportedException e) {
            return new XRayData();
        }
    }
}
