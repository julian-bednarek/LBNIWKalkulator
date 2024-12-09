package com.julian.lbniwkalkulator.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.julian.lbniwkalkulator.R;

public class InputField extends LinearLayout {

    private TextView label;
    private EditText input;
    private Spinner list_input;
    private final int DEFAULT_INPUT_LEN = 5;

    public InputField(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void setTextColor(String textColor) {
        int color = Color.parseColor(textColor);
        input.setTextColor(color);
        input.setHintTextColor(color);
        label.setTextColor(color);
    }

    private void setUpListInput() {
        list_input.setVisibility(VISIBLE);
        input.setVisibility(GONE);
    }

    private void setCustomAttributes (Context context, AttributeSet attrset) {
        TypedArray attr = context.obtainStyledAttributes(attrset, R.styleable.InputField);
        boolean usingListInput =    attr.getBoolean(R.styleable.InputField_using_list_input, false);
        String fieldLabel =         attr.getString(R.styleable.InputField_labelText);
        int maxLength =             attr.getInt(R.styleable.InputField_maxLength, DEFAULT_INPUT_LEN);
        int fieldType =             attr.getInt(R.styleable.InputField_android_inputType, 0);
        String textColor =          attr.getString(R.styleable.InputField_android_textColor);
        String placeholderText =    attr.getString(R.styleable.InputField_placeholderText);
        attr.recycle();
        
        if(usingListInput) {
            setUpListInput();
        }
        label.setText(fieldLabel);
        if (textColor != null) {
            setTextColor(textColor);
        }
        input.setInputType(fieldType);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        assert placeholderText != null;
        input.setHint(placeholderText);
    }

    private void init(Context context, AttributeSet attrset) {
        LayoutInflater.from(context).inflate(R.layout.input_field, this, true);

        label = findViewById(R.id.input_label);
        input = findViewById(R.id.input_value);
        list_input = findViewById(R.id.input_spinner);
        setCustomAttributes(context, attrset);
    }
}
