package com.julian.lbniwkalkulator.components.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.components.inputfields.NumberInput;
import com.julian.lbniwkalkulator.dataclasess.IsotopeActivity;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.util.CustomInputParsers;

public class CustomInsertInputDialog extends Dialog {

    public CustomInsertInputDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_insert_dialog);
    }

    public IsotopeActivity collectData() throws InputNotSupportedException {
        NumberInput isotopeNameField = findViewById(R.id.insert_name_input);
        NumberInput currentActivityField = findViewById(R.id.insert_activity_input);
        NumberInput halfLifeTimeField = findViewById(R.id.insert_half_life_input);
        String name = isotopeNameField.getInputValue();
        double currentActivity = CustomInputParsers.parseInputDouble(currentActivityField.getInputValue());
        double halfLifeTime = CustomInputParsers.parseInputDouble(halfLifeTimeField.getInputValue());
        return new IsotopeActivity(-1, name, halfLifeTime, -1, currentActivity);
    }
}
