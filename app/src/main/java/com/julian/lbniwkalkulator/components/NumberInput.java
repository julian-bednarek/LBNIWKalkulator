package com.julian.lbniwkalkulator.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.exceptions.MissingComponentParameterException;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;

public class NumberInput extends TextInputLayout {

    private TextInputEditText actualInput;
    private final int DEFAULT_MAX_INPUT_LEN = 5;

    public NumberInput(Context context, AttributeSet attrset) throws InvalidComponentException {
        super(context, attrset);
        init(context, attrset);
    }

    private void setUpAttributes(String suffix, String placeholder, int inputType, int maxLength) throws MissingComponentParameterException {
        if(suffix == null) suffix = "";
        setSuffixText(suffix);
        if(placeholder == null) throw new MissingComponentParameterException("Some input field is missing a placeholder value", "placeholder");
        actualInput.setHint(placeholder);
        actualInput.setInputType(inputType);
        actualInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public void init(Context context, AttributeSet attrset) throws MissingComponentParameterException {
        LayoutInflater.from(context).inflate(R.layout.number_input, this);
        actualInput = findViewById(R.id.actual_input);
        TypedArray attributes = context.obtainStyledAttributes(attrset, R.styleable.NumberInput);
        String suffix = attributes.getString(R.styleable.NumberInput_suffixText);
        String placeholder = attributes.getString(R.styleable.NumberInput_android_hint);
        int inputType = attributes.getInt(R.styleable.NumberInput_android_inputType, 0);
        int maxLength = attributes.getInt(R.styleable.NumberInput_maxLength, DEFAULT_MAX_INPUT_LEN);
        attributes.recycle();
        setUpAttributes(suffix, placeholder, inputType, maxLength);
    }
}
