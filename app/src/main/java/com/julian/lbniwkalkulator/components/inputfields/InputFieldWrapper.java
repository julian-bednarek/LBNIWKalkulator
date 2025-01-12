package com.julian.lbniwkalkulator.components.inputfields;

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
import com.julian.lbniwkalkulator.util.StringGetter;

/**
 * A custom wrapper for input fields, which can be either a text input (which is also custom) or a spinner (dropdown list).
 */
public class InputFieldWrapper extends LinearLayout {

    private TextView label;
    private NumberInput textInput;
    private InputFieldSpinner listInput;
    private boolean listInputVisible;

    public InputFieldWrapper(Context context, AttributeSet attrs) throws InvalidComponentException {
        super(context, attrs);
        init(context, attrs);
        setClickListener(context);
    }

    /**
     * Function responsible for enabling input by clicking at any point on wrapper not just actual input fields
     */
    private void setClickListener(Context context) {
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
        listInput.setVisibility(VISIBLE);
        textInput.setVisibility(GONE);
    }

    private void setUpLabel(String fieldLabel) throws MissingComponentParameterException {
        if(fieldLabel == null) throw new MissingComponentParameterException(StringGetter.
                fromStringsXML(R.string.exception_missing_component_parameter_exception_message), "labelText");
        label.setText(fieldLabel);
    }

    private void setUpPlaceholder(String placeholder) throws MissingComponentParameterException {
        if(placeholder == null) throw new MissingComponentParameterException(StringGetter.
                fromStringsXML(R.string.exception_missing_component_parameter_exception_message), "hint");
        textInput.setPlaceholder(placeholder);
    }

    /**
     * Function mainly for retrieval of custom arguments
     * @throws InvalidComponentException from setUpLabel and setUpPlaceholder
     */
    private void init(Context context, AttributeSet attrset) throws InvalidComponentException {
        LayoutInflater.from(context).inflate(R.layout.components_input_field, this, true);
        label = findViewById(R.id.input_label);
        textInput = findViewById(R.id.text_input);
        listInput = findViewById(R.id.input_spinner);
        TypedArray attributes = context.obtainStyledAttributes(attrset, R.styleable.InputFieldWrapper);
        String fieldLabel = attributes.getString(R.styleable.InputFieldWrapper_labelText);
        listInputVisible = attributes.getBoolean(R.styleable.InputFieldWrapper_using_list_input, false);
        String possibleEnum = attributes.getString(R.styleable.InputFieldWrapper_enum_type);
        String textInputPlaceholder = attributes.getString(R.styleable.InputFieldWrapper_android_hint);
        attributes.recycle();
        setUpLabel(fieldLabel);
        setUpPlaceholder(textInputPlaceholder);
        textInput.init(context, attrset);
        if(listInputVisible) {
            listInput.setEnum(possibleEnum);
            setUpListInput();
        }
    }

    public String getInputValue() {
        if(listInputVisible) {
            return listInput.getInputValue();
        } else {
            return textInput.getInputValue();
        }
    }

    public NumberInput getTextInput() {
        return textInput;
    }
}
