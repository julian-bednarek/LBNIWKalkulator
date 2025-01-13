package com.julian.lbniwkalkulator.components.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.julian.lbniwkalkulator.R;

public class CustomDeleteRecordsDialog extends Dialog {
    public CustomDeleteRecordsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_dialog);
    }

    public Button getYesButton() {
        return findViewById(R.id.yes_button);
    }

    public Button getNoButton() {
        return findViewById(R.id.no_button);
    }
}
