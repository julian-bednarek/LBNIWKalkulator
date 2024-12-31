package com.julian.lbniwkalkulator.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.julian.lbniwkalkulator.R;

public class NumberInput extends LinearLayout {

    private EditText input;
    private TextView inputUnit;
    private static final int MAX_INPUT_LEN = 5;

    private void setUpInput(int type) {
        input.setInputType(type);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_INPUT_LEN)});
    }

    private void setUpInputUnit(String unit) {
        if(unit == null) unit = "";
        inputUnit.setText(unit);
    }

    public void init(Context context, AttributeSet attrset) {
        LayoutInflater.from(context).inflate(R.layout.number_input, this, true);
        input = findViewById(R.id.actual_input);
        inputUnit = findViewById(R.id.suffix_label);
        TypedArray attributes = context.obtainStyledAttributes(attrset, R.styleable.NumberInput);
        int inputType = attributes.getInt(R.styleable.NumberInput_android_inputType, 0);
        String unit = attributes.getString(R.styleable.NumberInput_suffixText);
        attributes.recycle();
        setUpInput(inputType);
        setUpInputUnit(unit);
    }

    public void setPlaceholder(String placeholder) {
        input.setHint(placeholder);
    }

    public NumberInput(Context context, AttributeSet attrset) {
        super(context, attrset);
        init(context, attrset);
    }

    public String getInputValue() {
        return input.getText().toString();
    }

    public EditText getInput() {
        return input;
    }
}
