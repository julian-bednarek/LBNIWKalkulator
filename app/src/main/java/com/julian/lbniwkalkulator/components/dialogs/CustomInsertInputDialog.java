package com.julian.lbniwkalkulator.components.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.components.inputfields.NumberInput;
import com.julian.lbniwkalkulator.dataclasess.IsotopeActivity;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.util.CustomInputParsers;
import com.julian.lbniwkalkulator.util.StringGetter;

public class CustomInsertInputDialog extends Dialog {

    public CustomInsertInputDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_insert_dialog);
        clearInputs();
    }

    public Button getAddButton() {
        return findViewById(R.id.insert_button);
    }

    public double toSeconds(double days) {
        return days * 24 * 60 * 60;
    }

    public IsotopeActivity collectData() throws InputNotSupportedException {
        NumberInput isotopeNameField = findViewById(R.id.insert_name_input);
        NumberInput currentActivityField = findViewById(R.id.insert_activity_input);
        NumberInput halfLifeTimeField = findViewById(R.id.insert_half_life_input);
        String name = isotopeNameField.getInputValue();
        double currentActivity = CustomInputParsers.parseInputDouble(currentActivityField.getInputValue());
        double halfLifeTimeInDays = CustomInputParsers.parseInputDouble(halfLifeTimeField.getInputValue());
        return new IsotopeActivity(-1, name, toSeconds(halfLifeTimeInDays), -1, currentActivity);
    }

    public void clearInputs() {
        String decimalPlaceholder = StringGetter.fromStringsXML(R.string.decimal_placeholder);
        ((NumberInput) findViewById(R.id.insert_name_input)).getInput().setHint("");
        ((NumberInput) findViewById(R.id.insert_activity_input)).getInput().setHint(decimalPlaceholder);
        ((NumberInput) findViewById(R.id.insert_half_life_input)).getInput().setHint(decimalPlaceholder);
    }
}
