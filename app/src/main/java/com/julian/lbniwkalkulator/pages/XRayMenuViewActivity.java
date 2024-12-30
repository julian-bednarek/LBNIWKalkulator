package com.julian.lbniwkalkulator.pages;

import static com.julian.lbniwkalkulator.util.ErrorHandler.getActualCause;
import static com.julian.lbniwkalkulator.util.ErrorHandler.processException;

import android.content.Intent;
import android.os.Bundle;
import android.view.InflateException;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.CustomInputParsers;
import com.julian.lbniwkalkulator.enums.FilmTypeEnum;
import com.julian.lbniwkalkulator.calculations.dataclasess.XRayData;
import com.julian.lbniwkalkulator.components.InputFieldWrapper;
import com.julian.lbniwkalkulator.enums.VoltageValuesEnum;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;

public class XRayMenuViewActivity extends AbstractInputMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generalSetUp(R.layout.x_ray_menu_layout, R.id.calculate_button_xray);
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
