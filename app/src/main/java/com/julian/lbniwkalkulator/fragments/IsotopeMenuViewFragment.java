package com.julian.lbniwkalkulator.fragments;

import static androidx.navigation.Navigation.findNavController;
import static com.julian.lbniwkalkulator.util.CustomInputParsers.parseInputDouble;
import static com.julian.lbniwkalkulator.util.CustomInputParsers.parseInputInt;
import static com.julian.lbniwkalkulator.util.ErrorHandler.processException;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.components.inputfields.InputFieldWrapper;
import com.julian.lbniwkalkulator.databinding.ViewIsotopeMenuLayoutBinding;
import com.julian.lbniwkalkulator.dataclasess.IsotopeData;
import com.julian.lbniwkalkulator.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.enums.FilmTypeEnum;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

public class IsotopeMenuViewFragment extends AbstractInputMenuFragment{

    ViewIsotopeMenuLayoutBinding binding;

    public IsotopeMenuViewFragment() {
        super(R.layout.view_isotope_menu_layout);
    }

    @Override
    protected void setUpCalculateButton(@NonNull View view, int buttonID) {
        binding.calculateButtonIsotope.setOnClickListener(v -> {
            RadiationData checkData = collectData(view);
            if(checkData != null) {
                findNavController(v).navigate(IsotopeMenuViewFragmentDirections.
                        actionIsotopeMenuViewFragmentToCalculatedTimeViewFragment(checkData));
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view,
                             @Nullable Bundle savedInstanceState) {
        binding = ViewIsotopeMenuLayoutBinding.bind(view);
        generalSetUp(view, R.id.calculate_button_isotope);
        ((RadioButton) view.findViewById(R.id.isotope_from_saved_radio)).setChecked(true);
    }

    @Override
    protected RadiationData collectData(View rootView) {
        try {
            double activity = parseInputDouble(((InputFieldWrapper) rootView
                                    .findViewById(R.id.activity_input_isotope))
                                    .getInputValue());
            double targetDensity = parseInputDouble(((InputFieldWrapper) rootView
                    .findViewById(R.id.target_density_isotope))
                    .getInputValue());
            int sourceToDetectorDistance = parseInputInt(((InputFieldWrapper) rootView
                    .findViewById(R.id.distance_from_detector_isotope))
                    .getInputValue());
            double steelThickness = parseInputDouble(((InputFieldWrapper) rootView
                    .findViewById(R.id.material_thickness_isotope))
                    .getInputValue());
            String isotopeType = (((InputFieldWrapper) rootView
                    .findViewById(R.id.isotope_type_isotope))
                    .getInputValue());
            int filmType = FilmTypeEnum.valueFromString(((InputFieldWrapper) rootView
                    .findViewById(R.id.film_type_isotope))
                    .getInputValue());
            return new IsotopeData(activity, steelThickness, sourceToDetectorDistance,
                    isotopeType, filmType, targetDensity);
        } catch (InputNotSupportedException e) {
            processException(requireContext(), e.getMessage(), null, null, null);
            return null;
        }
    }
}
