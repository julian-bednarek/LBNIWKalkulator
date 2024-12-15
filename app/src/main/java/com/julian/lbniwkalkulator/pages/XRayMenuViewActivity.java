package com.julian.lbniwkalkulator.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.InflateException;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.CustomInputParsers;
import com.julian.lbniwkalkulator.calculations.FilmTypeEnum;
import com.julian.lbniwkalkulator.calculations.XRayData;
import com.julian.lbniwkalkulator.components.InputFieldWrapper;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;
import com.julian.lbniwkalkulator.pages.CalculatedTimeViewAcitivity;

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
        Button calculateButton = findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(view -> {
            XRayData data = null;
            try {
                data = collectData();
            } catch (InputNotSupportedException e) {
                throw new RuntimeException(e);
            }
            Intent intent = new Intent(XRayMenuViewActivity.this, CalculatedTimeViewAcitivity.class);
            intent.putExtra("x_ray_input_data", data);
            startActivity(intent);
        });
    }

    private XRayData collectData() throws InputNotSupportedException {

        //int voltage = Integer.parseInt(((InputFieldWrapper) findViewById(R.id.inputXRayVoltage)).getInputValue());
        int current = CustomInputParsers.parseInputInt(((InputFieldWrapper) findViewById(R.id.inputXRayCurrent)).getInputValue());
        double steelThickness = CustomInputParsers.parseInputDouble(((InputFieldWrapper) findViewById(R.id.inputXRaySteelThickness)).getInputValue());
        //FilmTypeEnum filmType = FilmTypeEnum.valueOf(((InputFieldWrapper) findViewById(R.id.inputXRayFilmType)).getInputValue());
        double sourceToDetectorDistance = CustomInputParsers.parseInputDouble(((InputFieldWrapper) findViewById(R.id.inputXRaySourceToDetector)).getInputValue());
        double targetDensity = CustomInputParsers.parseInputDouble(((InputFieldWrapper) findViewById(R.id.inputXRayTargetDensity)).getInputValue());
        return new XRayData(0, current, steelThickness, FilmTypeEnum._D1, sourceToDetectorDistance, targetDensity);
    }
}
