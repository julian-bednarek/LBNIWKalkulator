package com.julian.lbniwkalkulator.components.inputfields;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.enums.InputEnumTypes;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentParameterException;
import com.julian.lbniwkalkulator.exceptions.MissingComponentParameterException;
import com.julian.lbniwkalkulator.util.StringGetter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Custom spinner component designed to display an enum-based list of options.
 * <p>
 * This class is used for selecting a value from a list derived from an enum type.
 * It populates the spinner with enum constants and handles validation of the enum type input.
 * </p>
 */
public class InputFieldSpinner extends androidx.appcompat.widget.AppCompatSpinner {

    public InputFieldSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets the enum type for the spinner and populates it with the enum constants.
     *
     * @param enumType The name of the enum type to be used for the spinner options.
     * @throws MissingComponentParameterException if the enumType is null.
     * @throws InvalidComponentParameterException if the provided enumType is not valid.
     */
    public void setEnum(String enumType) throws MissingComponentParameterException, InvalidComponentParameterException {
        if(enumType == null) throw new MissingComponentParameterException(
                StringGetter.fromStringsXML(R.string.exception_missing_component_parameter_exception_message),
                "enum_type");
        boolean validEnum = Arrays.stream(InputEnumTypes.values()).anyMatch(e -> e.name().equals(enumType.toUpperCase()));
        if(!validEnum) throw new InvalidComponentParameterException(
                StringGetter.fromStringsXML(R.string.exception_invalid_component_parameter_exception_message),
                enumType);
        InputEnumTypes eType = InputEnumTypes.valueOf(enumType.toUpperCase());
        fillData(eType.getEnumClass());
    }

    private void fillData(Class<? extends Enum<?>> enumClass) {
        List<String> spinnerData = new ArrayList<>();
        for (Enum<?> enumConstant : Objects.requireNonNull(enumClass.getEnumConstants())) {
            String displayName = enumConstant.name().replace("_", " ");
            spinnerData.add(displayName);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.components_item_list_main_field, spinnerData);
        adapter.setDropDownViewResource(R.layout.components_item_list_dropdown_field);
        setAdapter(adapter);
    }
    /**
     * Retrieves the currently selected value from the spinner.
     *
     * @return The selected value as a string, or an empty string if no selection is made.
     */
    public String getInputValue() {
        Object selectedItem = getSelectedItem();
        return selectedItem != null ? selectedItem.toString() : "";
    }
}