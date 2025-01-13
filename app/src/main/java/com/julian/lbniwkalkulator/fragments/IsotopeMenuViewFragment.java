package com.julian.lbniwkalkulator.fragments;

import static androidx.navigation.Navigation.findNavController;
import static com.julian.lbniwkalkulator.components.inputfields.PopupListView.ISOTOPE_ENUM;
import static com.julian.lbniwkalkulator.components.inputfields.PopupListView.ISOTOPE_FROM_DATABASE;
import static com.julian.lbniwkalkulator.components.inputfields.PopupListView.ISOTOPE_FROM_DATABASE_REGEX;
import static com.julian.lbniwkalkulator.components.inputfields.PopupListView.processSelection;
import static com.julian.lbniwkalkulator.util.CustomInputParsers.parseInputDouble;
import static com.julian.lbniwkalkulator.util.CustomInputParsers.parseInputInt;
import static com.julian.lbniwkalkulator.util.ErrorHandler.processException;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.components.inputfields.InputFieldWrapper;
import com.julian.lbniwkalkulator.components.inputfields.PopupListView;
import com.julian.lbniwkalkulator.databinding.ViewIsotopeMenuLayoutBinding;
import com.julian.lbniwkalkulator.dataclasess.IsotopeData;
import com.julian.lbniwkalkulator.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.enums.FilmTypeEnum;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view,
                             @Nullable Bundle savedInstanceState) {
        binding = ViewIsotopeMenuLayoutBinding.bind(view);
        generalSetUp(view, R.id.calculate_button_isotope);
        ((RadioButton) view.findViewById(R.id.custom_isotope_radio)).setChecked(true);
        binding.isotopeMenuBackButton.setOnClickListener(v -> {
            findNavController(view).navigate(IsotopeMenuViewFragmentDirections.
                    actionIsotopeMenuViewFragmentToStartViewFragment());
        });
        setUpIsotopeTypeListener();
        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.isotope_from_saved_radio:
                    binding.isotopeTypeIsotope.toggleIsotopeData(ISOTOPE_FROM_DATABASE);
                    break;
                case R.id.custom_isotope_radio:
                    binding.isotopeTypeIsotope.toggleIsotopeData(ISOTOPE_ENUM);
                    break;
            }
            binding.isotopeTypeIsotope.resetCurrentSelection();
        });
    }

    private void setUpIsotopeTypeListener() {
        PopupListView popupListView = binding.isotopeTypeIsotope.getPopupListView();
        popupListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selectedValueText = requireActivity().findViewById(R.id.selected_value_text);
                String currentSelection = parent.getAdapter().getItem(position).toString();
                String actualSelection = processSelection(currentSelection);
                selectedValueText.setText(actualSelection);
                binding.isotopeTypeIsotope.setCurrentSelection(currentSelection);
                conditionallySetActivityInput(currentSelection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void conditionallySetActivityInput(String actualSelection) {
        if(actualSelection.matches(ISOTOPE_FROM_DATABASE_REGEX)) {
            Pattern pattern = Pattern.compile(ISOTOPE_FROM_DATABASE_REGEX);
            Matcher matcher = pattern.matcher(actualSelection);
            if(matcher.find()) {
                binding.activityInputIsotope.getTextInput().getInput().setText(matcher.group(1));
            }
        } else {
            binding.activityInputIsotope.getTextInput().getInput().setText("");
        }
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
