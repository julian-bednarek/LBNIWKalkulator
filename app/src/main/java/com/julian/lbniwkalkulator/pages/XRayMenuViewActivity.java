package com.julian.lbniwkalkulator.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.InflateException;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.CustomInputParsers;
import com.julian.lbniwkalkulator.enums.FilmTypeEnum;
import com.julian.lbniwkalkulator.calculations.dataclasess.XRayData;
import com.julian.lbniwkalkulator.components.InputFieldWrapper;
import com.julian.lbniwkalkulator.enums.VoltageValuesEnum;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;

public class XRayMenuViewActivity extends AppCompatActivity {

    /**
     * In catch clause, cause chain is expected to be:
     * InflateException -> InvocationTargetException -> InvalidComponentException or its subclasses
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.x_ray_menu_layout);
            setUpCalculateButton();
        } catch (InflateException e) {
            Throwable cause = e;
            Throwable actualCause = e;
            while (cause.getCause() != null) {
                cause = cause.getCause();
                actualCause = cause;
            }
            if (actualCause instanceof InvalidComponentException) {
                InvalidComponentException ex = (InvalidComponentException) actualCause;
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void setUpCalculateButton() {
        Button calculateButton = findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(view -> {
            XRayData data = collectData();
            Intent intent = new Intent(XRayMenuViewActivity.this, CalculatedTimeViewActivity.class);
            intent.putExtra("input_data", data);
            startActivity(intent);
        });
    }

    private XRayData collectData() {
        try {
            int voltage = VoltageValuesEnum.valueFromString(((InputFieldWrapper) findViewById(R.id.inputXRayVoltage)).getInputValue());
            int current = CustomInputParsers.parseInputInt(((InputFieldWrapper) findViewById(R.id.inputXRayCurrent)).getInputValue());
            double steelThickness = CustomInputParsers.parseInputDouble(((InputFieldWrapper) findViewById(R.id.inputXRaySteelThickness)).getInputValue());
            int filmType = FilmTypeEnum.valueFromString(((InputFieldWrapper) findViewById(R.id.inputXRayFilmType)).getInputValue());
            double sourceToDetectorDistance = CustomInputParsers.parseInputDouble(((InputFieldWrapper) findViewById(R.id.inputXRaySourceToDetector)).getInputValue());
            double targetDensity = CustomInputParsers.parseInputDouble(((InputFieldWrapper) findViewById(R.id.inputXRayTargetDensity)).getInputValue());
            return new XRayData(voltage, current, steelThickness, filmType, sourceToDetectorDistance, targetDensity);
        } catch (InputNotSupportedException e) {
            return new XRayData();
        }
    }
}
