package com.julian.lbniwkalkulator.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.InputEnumTypes;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentParameterException;
import com.julian.lbniwkalkulator.exceptions.MissingComponentParameterException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InputFieldSpinner extends androidx.appcompat.widget.AppCompatSpinner {

    private ArrayAdapter<String> adapter;

    public InputFieldSpinner(Context context, AttributeSet attrs) throws InvalidComponentException {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) throws MissingComponentParameterException, InvalidComponentParameterException {
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.InputFieldSpinner);
        String enumType = attr.getString(R.styleable.InputFieldSpinner_enum_type);
        attr.recycle();
        if(enumType == null) throw new MissingComponentParameterException("Enum type is missing", "enum_type");
        boolean validEnum = Arrays.stream(InputEnumTypes.values()).anyMatch(e -> e.name().equals(enumType.toUpperCase()));
        if(!validEnum) throw new InvalidComponentParameterException("Invalid enum type", enumType);
        InputEnumTypes eType = InputEnumTypes.valueOf(enumType.toUpperCase());
        fillData(eType.getEnumClass());
    }

    private <T extends Enum<T>> void fillData(Class<? extends Enum<?>> enumClass) {
        List<String> spinnerData = new ArrayList<>();
        for (Enum<?> enumConstant : Objects.requireNonNull(enumClass.getEnumConstants())) {
            String displayName = enumConstant.name().replace("_", " ");
            spinnerData.add(displayName);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_list_main_field, spinnerData);
        adapter.setDropDownViewResource(R.layout.item_list_dropdown_field);
        setAdapter(adapter);
    }
}