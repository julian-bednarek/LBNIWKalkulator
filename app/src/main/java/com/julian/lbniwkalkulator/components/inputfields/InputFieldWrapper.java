package com.julian.lbniwkalkulator.components.inputfields;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentParameterException;
import com.julian.lbniwkalkulator.exceptions.MissingComponentParameterException;
import com.julian.lbniwkalkulator.util.StringGetter;

import java.util.List;

/**
 * A custom wrapper for input fields, which can be either a text input or a popup list view.
 */
public class InputFieldWrapper extends LinearLayout {

    private TextView label;
    private NumberInput textInput;
    private PopupListView popupListView;
    private boolean listInputVisible;
    public static final String defaultSelection = StringGetter.fromStringsXML(R.string.select_);
    private String currentSelection = defaultSelection;
    private String possibleEnum;

    public InputFieldWrapper(Context context, AttributeSet attrs) throws InvalidComponentException {
        super(context, attrs);
        init(context, attrs);
        setClickListener(context);
    }

    /**
     * Function responsible for enabling input by clicking at any point on wrapper
     */
    private void setClickListener(Context context) {
        EditText editText = findViewById(R.id.actual_input);
        setOnClickListener(v -> {
            if (listInputVisible) {
                editText.clearFocus();
                showPopupList();
            } else {
                editText.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    public void setCurrentSelection(String currentSelection) {
        this.currentSelection = currentSelection;
    }

    public PopupListView getPopupListView() {
        return popupListView;
    }

    public void resetCurrentSelection() {
        currentSelection = defaultSelection;
        ((TextView)findViewById(R.id.selected_value_text)).setText(currentSelection);
    }

    private void showPopupList() {
        popupListView.show();
    }

    private String processSelection(String selectedItem) {
        final String ISOTOPE_FROM_DATABASE_REGEX = "\\S+:\\t{5}[\\d\\.]+ Ci";
        if(selectedItem.matches(ISOTOPE_FROM_DATABASE_REGEX)) {
            return selectedItem.split(":")[0];
        } else {
            return selectedItem;
        }
    }

    private void setUpListInput(Context context, AttributeSet attributeSet, String listTitle) throws MissingComponentParameterException {
        TextView selectedValueText = findViewById(R.id.selected_value_text);
        popupListView = new PopupListView(context, attributeSet);
        popupListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getAdapter().getItem(position).toString();
                currentSelection = selectedItem;
                selectedValueText.setText(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        if(listTitle == null) throw new MissingComponentParameterException(StringGetter.fromStringsXML(R.string.exception_missing_component_parameter_exception_message), "list_title");
        popupListView.getTitle().setText(listTitle);
        selectedValueText.setVisibility(VISIBLE);
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
        ((TextView)findViewById(R.id.selected_value_text)).setText(currentSelection);
        TypedArray attributes = context.obtainStyledAttributes(attrset, R.styleable.InputFieldWrapper);
        String fieldLabel = attributes.getString(R.styleable.InputFieldWrapper_labelText);
        listInputVisible = attributes.getBoolean(R.styleable.InputFieldWrapper_using_list_input, false);
        possibleEnum = attributes.getString(R.styleable.InputFieldWrapper_enum_type);
        String textInputPlaceholder = attributes.getString(R.styleable.InputFieldWrapper_android_hint);
        String listTitle = attributes.getString(R.styleable.InputFieldWrapper_list_title);
        attributes.recycle();

        setUpLabel(fieldLabel);
        setUpPlaceholder(textInputPlaceholder);

        if(listInputVisible) {
            setUpListInput(context, attrset, listTitle);
            try {
                popupListView.setOrUpdateContents(possibleEnum);
            } catch (InvalidComponentParameterException | MissingComponentParameterException e) {
                throw new InvalidComponentException(e.getMessage());
            }
        } else {
            textInput.init(context, attrset);
        }
    }

    public String getInputValue() {
        return listInputVisible ? currentSelection : textInput.getInputValue();
    }
    public NumberInput getTextInput() {
        return textInput;
    }

    /**
     * I hope it's only temporary
     * @return string with value because it needs to be set on activity field
     */
    public String getActivityFromIsotopeFromDatabase() {
        return currentSelection.split("\t\t\t\t\t")[1];
    }

    /**
     * Used only in case of voltage
     * @param range maximum value of voltage
     */
    public void setRange(int range) {
        popupListView.setRange(range);
        try {
            popupListView.setOrUpdateContents(possibleEnum);
        } catch (InvalidComponentParameterException | MissingComponentParameterException e) {
            throw new RuntimeException(e);
        }
    }

    public void toggleIsotopeData(String data) {
        try {
            popupListView.setOrUpdateContents(data);
        } catch (InvalidComponentParameterException | MissingComponentParameterException e) {
            throw new RuntimeException(e);
        }
    }
}