package com.julian.lbniwkalkulator.components.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.julian.lbniwkalkulator.R;

public class CustomErrorDialog extends AlertDialog {
    private final String errorMessage;
    private final String errorTitle;
    private final String dismissButtonText;

    public CustomErrorDialog(@NonNull Context context, String errorMessage, String errorTitle, String dismissButtonText) {
        super(context);
        this.errorMessage = errorMessage;
        this.errorTitle = errorTitle;
        this.dismissButtonText = dismissButtonText;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_error_dialog);

        TextView titleView = findViewById(R.id.error_title);
        TextView messageView = findViewById(R.id.error_message);
        Button dismissButton = findViewById(R.id.ok_button);

        if (titleView != null) titleView.setText(errorTitle);
        if (messageView != null) messageView.setText(errorMessage);
        if (dismissButton != null) {
            dismissButton.setText(dismissButtonText);
            dismissButton.setOnClickListener(view -> dismiss());
        }

        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setGravity(Gravity.CENTER);

            // Set the dialog width to match parent with margins
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }

        setCancelable(false);
    }

    public static class Builder {
        private final Context context;
        private String message;
        private String title;
        private String dismissButtonText;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDismissButtonText(String dismissButtonText) {
            this.dismissButtonText = dismissButtonText;
            return this;
        }

        public CustomErrorDialog create() {
            return new CustomErrorDialog(context, message, title, dismissButtonText);
        }
    }
}