package com.julian.lbniwkalkulator.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;
import com.julian.lbniwkalkulator.exceptions.MissingComponentParameterException;

public class InputFieldWrapper extends LinearLayout {

    private TextView label;
    private NumberInput text_input;
    private InputFieldSpinner list_input;

    public InputFieldWrapper(Context context, AttributeSet attrs) throws InvalidComponentException {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * If parameter is false, there is no need to change anything
     * @param listInputVisible determining whether we use enum type input or text
     */
    private void setUpListInput(boolean listInputVisible) {
        if(listInputVisible) {
            list_input.setVisibility(VISIBLE);
            text_input.setVisibility(GONE);
        }
    }

    private void setUpLabel(String fieldLabel) throws MissingComponentParameterException {
        if(fieldLabel == null) throw new MissingComponentParameterException("There is a missing parameter input field component", "labelText");
        label.setText(fieldLabel);
    }

    private void init(Context context, AttributeSet attrset) throws InvalidComponentException{
        LayoutInflater.from(context).inflate(R.layout.input_field, this, true);
        label = findViewById(R.id.input_label);
        text_input = findViewById(R.id.text_input);
        list_input = findViewById(R.id.input_spinner);
        TypedArray attributes = context.obtainStyledAttributes(attrset, R.styleable.InputFieldWrapper);
        String fieldLabel = attributes.getString(R.styleable.InputFieldWrapper_labelText);
        boolean listInputVisible = attributes.getBoolean(R.styleable.InputFieldWrapper_using_list_input, false);
        attributes.recycle();
        setUpLabel(fieldLabel);
        setUpListInput(listInputVisible);
    }
}
