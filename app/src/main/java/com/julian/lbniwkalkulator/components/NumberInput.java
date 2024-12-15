package com.julian.lbniwkalkulator.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.julian.lbniwkalkulator.R;

public class NumberInput extends TextInputLayout {

    private TextInputEditText actualInput;

    public NumberInput(Context context, AttributeSet attrset) {
        super(context, attrset);
        init(context, attrset);
    }

    public void init(Context context, AttributeSet attrset) {
        LayoutInflater.from(context).inflate(R.layout.number_input, this);
        actualInput = findViewById(R.id.actual_input);
    }
}
