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
import com.julian.lbniwkalkulator.calculations.dataclasess.IsotopeData;
import com.julian.lbniwkalkulator.components.InputFieldWrapper;
import com.julian.lbniwkalkulator.enums.FilmTypeEnum;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;

public class IsotopeMenuViewActivity extends AppCompatActivity {

    //TODO: Finish it
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.isotope_menu_layout);
            setUpCalculateButton();
        } catch (InflateException e) {
            Throwable actualCause = getActualCause(e);
            if (actualCause instanceof InvalidComponentException) {
                InvalidComponentException ex = (InvalidComponentException) actualCause;
                processException(this, ex.getMessage(), null, null, this::finish);
            }
        }
    }

    private void setUpCalculateButton() {
        Button calculateButton = findViewById(R.id.calculate_button_isotope);
        calculateButton.setOnClickListener(view -> {
            IsotopeData data = collectData();
            Intent intent = new Intent(IsotopeMenuViewActivity.this, CalculatedTimeViewActivity.class);
            intent.putExtra("input_data", data);
            startActivity(intent);
        });
    }

    private IsotopeData collectData() {
        try {
            double activity = CustomInputParsers.parseInputDouble(((InputFieldWrapper) findViewById(R.id.activity_input_isotope)).getInputValue());
            double targetDensity = CustomInputParsers.parseInputDouble(((InputFieldWrapper) findViewById(R.id.target_density_isotope)).getInputValue());
            int sourceToDetectorDistance = CustomInputParsers.parseInputInt(((InputFieldWrapper) findViewById(R.id.distance_from_detector_isotope)).getInputValue());
            double steelThickness = CustomInputParsers.parseInputDouble(((InputFieldWrapper) findViewById(R.id.material_thickness_isotope)).getInputValue());
            String isotopeType = (((InputFieldWrapper) findViewById(R.id.isotope_type_isotope)).getInputValue());
            int filmType = FilmTypeEnum.valueFromString(((InputFieldWrapper) findViewById(R.id.film_type_isotope)).getInputValue());
            return new IsotopeData(activity, steelThickness, sourceToDetectorDistance, isotopeType, filmType, targetDensity);
        } catch (InputNotSupportedException e) {
            return new IsotopeData();
        }
    }
}
