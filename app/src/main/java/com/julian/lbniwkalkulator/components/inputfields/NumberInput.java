package com.julian.lbniwkalkulator.components.inputfields;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.julian.lbniwkalkulator.R;

import javax.annotation.Nullable;

public class NumberInput extends LinearLayout {

    private EditText input;
    private TextView inputUnit;
    private static final int MAX_INPUT_LEN = 5;

    public NumberInput(Context context, AttributeSet attrset) {
        super(context, attrset);
        init(context, attrset);
    }

    /**
     * Function responsible for enabling input by clicking at any point on wrapper not just actual input fields
     */
    public void init(Context context, AttributeSet attrset) {
        LayoutInflater.from(context).inflate(R.layout.components_number_input, this, true);
        input = findViewById(R.id.actual_input);
        inputUnit = findViewById(R.id.suffix_label);
        TypedArray attributes = context.obtainStyledAttributes(attrset, R.styleable.NumberInput);
        int inputType = attributes.getInt(R.styleable.NumberInput_android_inputType, 0);
        String unit = attributes.getString(R.styleable.NumberInput_suffixText);
        attributes.recycle();
        setUpInput(inputType);
        setUpInputUnit(unit);
    }

    private void setUpInput(int type) {
        input.setInputType(type);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_INPUT_LEN)});
    }

    private void setUpInputUnit(@Nullable String unit) {
        if(unit == null) unit = "";
        inputUnit.setText(unit);
    }

    public void setPlaceholder(@Nullable String placeholder) {
        input.setHint(placeholder);
    }

    public String getInputValue() {
        return input.getText().toString();
    }

    public EditText getInput() {
        return input;
    }
}
