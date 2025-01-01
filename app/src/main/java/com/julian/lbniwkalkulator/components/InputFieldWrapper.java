package com.julian.lbniwkalkulator.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;
import com.julian.lbniwkalkulator.exceptions.MissingComponentParameterException;

public class InputFieldWrapper extends LinearLayout {

    private TextView label;
    private NumberInput text_input;
    private InputFieldSpinner list_input;
    private boolean listInputVisible;

    public InputFieldWrapper(Context context, AttributeSet attrs) throws InvalidComponentException {
        super(context, attrs);
        init(context, attrs);
        setStateListeners(context);
    }

    private void setStateListeners(Context context) {
        EditText editText = findViewById(R.id.actual_input);
        Spinner spinner = findViewById(R.id.input_spinner);
        setOnClickListener(v -> {

            if (spinner.getVisibility() == View.VISIBLE) {
                editText.clearFocus();
                spinner.performClick();
            } else {
                editText.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    private void setUpListInput() {
        list_input.setVisibility(VISIBLE);
        text_input.setVisibility(GONE);
    }

    private void setUpLabel(String fieldLabel) throws MissingComponentParameterException {
        if(fieldLabel == null) throw new MissingComponentParameterException("There is a missing parameter input field component", "labelText");
        label.setText(fieldLabel);
    }

    private void setUpPlaceholder(String placeholder) throws MissingComponentParameterException {
        if(placeholder == null) throw new MissingComponentParameterException("There is a missing placeholder in some input field/fields", "hint");
        text_input.setPlaceholder(placeholder);
    }

    private void init(Context context, AttributeSet attrset) throws InvalidComponentException{
        LayoutInflater.from(context).inflate(R.layout.input_field, this, true);
        label = findViewById(R.id.input_label);
        text_input = findViewById(R.id.text_input);
        list_input = findViewById(R.id.input_spinner);
        TypedArray attributes = context.obtainStyledAttributes(attrset, R.styleable.InputFieldWrapper);
        String fieldLabel = attributes.getString(R.styleable.InputFieldWrapper_labelText);
        listInputVisible = attributes.getBoolean(R.styleable.InputFieldWrapper_using_list_input, false);
        String possibleEnum = attributes.getString(R.styleable.InputFieldWrapper_enum_type);
        String textInputPlaceholder = attributes.getString(R.styleable.InputFieldWrapper_android_hint);
        attributes.recycle();
        setUpLabel(fieldLabel);
        setUpPlaceholder(textInputPlaceholder);
        text_input.init(context, attrset);
        if(listInputVisible) {
            list_input.setEnum(possibleEnum);
            setUpListInput();
        }
    }

    public String getInputValue() {
        if(listInputVisible) {
            return list_input.getInputValue();
        } else {
            return text_input.getInputValue();
        }
    }

    public NumberInput getText_input() {
        return text_input;
    }
}
