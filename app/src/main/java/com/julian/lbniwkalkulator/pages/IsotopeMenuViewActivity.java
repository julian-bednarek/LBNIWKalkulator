package com.julian.lbniwkalkulator.pages;

import android.os.Bundle;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.util.CustomInputParsers;
import com.julian.lbniwkalkulator.calculations.dataclasess.IsotopeData;
import com.julian.lbniwkalkulator.components.InputFieldWrapper;
import com.julian.lbniwkalkulator.enums.FilmTypeEnum;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

public class IsotopeMenuViewActivity extends AbstractInputMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generalSetUp(R.layout.isotope_menu_layout, R.id.calculate_button_isotope);
    }

    @Override
    protected IsotopeData collectData() {
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
