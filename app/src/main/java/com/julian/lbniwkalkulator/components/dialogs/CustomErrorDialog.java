package com.julian.lbniwkalkulator.components.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.julian.lbniwkalkulator.R;

public class CustomErrorDialog extends AlertDialog {
    public CustomErrorDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_error_dialog);
    }
}
