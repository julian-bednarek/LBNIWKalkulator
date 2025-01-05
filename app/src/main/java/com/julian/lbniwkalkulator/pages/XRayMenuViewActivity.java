package com.julian.lbniwkalkulator.pages;

import android.os.Bundle;
import android.widget.RadioButton;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.util.CustomInputParsers;
import com.julian.lbniwkalkulator.enums.FilmTypeEnum;
import com.julian.lbniwkalkulator.calculations.dataclasess.XRayData;
import com.julian.lbniwkalkulator.components.InputFieldWrapper;
import com.julian.lbniwkalkulator.enums.VoltageValuesEnum;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

public class XRayMenuViewActivity extends AbstractInputMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generalSetUp(R.layout.x_ray_menu_layout, R.id.calculate_button_xray);
        ((RadioButton)findViewById(R.id.lamp_y_smart)).setChecked(true);
    }

    @Override
    protected XRayData collectData() {
        try {
            int voltage = VoltageValuesEnum.valueFromString(((InputFieldWrapper) findViewById(R.id.inputXRayVoltage)).getInputValue());
            int current = CustomInputParsers.parseInputInt(((InputFieldWrapper) findViewById(R.id.inputXRayCurrent)).getInputValue());
            double steelThickness = CustomInputParsers.parseInputDouble(((InputFieldWrapper) findViewById(R.id.inputXRaySteelThickness)).getInputValue());
            int filmType = FilmTypeEnum.valueFromString(((InputFieldWrapper) findViewById(R.id.inputXRayFilmType)).getInputValue());
            int sourceToDetectorDistance = CustomInputParsers.parseInputInt(((InputFieldWrapper) findViewById(R.id.inputXRaySourceToDetector)).getInputValue());
            double targetDensity = CustomInputParsers.parseInputDouble(((InputFieldWrapper) findViewById(R.id.inputXRayTargetDensity)).getInputValue());
            return new XRayData(voltage, current, steelThickness, filmType, sourceToDetectorDistance, targetDensity);
        } catch (InputNotSupportedException e) {
            return new XRayData();
        }
    }
}
