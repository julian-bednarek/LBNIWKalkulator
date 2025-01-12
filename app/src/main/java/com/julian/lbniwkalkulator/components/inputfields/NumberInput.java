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
    private static final int MAX_INPUT_LEN_NUM = 7;
    private static final int MAX_INPUT_LEN_STR = 9;

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
        boolean usingStr = attributes.getBoolean(R.styleable.NumberInput_using_str, false);
        int inputType = attributes.getInt(R.styleable.NumberInput_android_inputType, 0);
        String unit = attributes.getString(R.styleable.NumberInput_suffixText);
        attributes.recycle();
        setUpInput(inputType, usingStr);
        setUpInputUnit(unit);
    }

    /**
     *
     * @param type type of input e.g. text, number, numberDecimal etc.
     * @param usingStr just type == text (but needed to add explicitly)
     */
    private void setUpInput(int type, boolean usingStr) {
        input.setInputType(type);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(usingStr ? MAX_INPUT_LEN_STR : MAX_INPUT_LEN_NUM)});
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

    public void setInputValue(String value) {
        input.setText(value);
    }
}
