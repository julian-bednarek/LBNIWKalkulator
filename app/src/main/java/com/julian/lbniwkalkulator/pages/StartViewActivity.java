package com.julian.lbniwkalkulator.pages;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.InflateException;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;
import com.julian.lbniwkalkulator.util.StringGetter;

import static com.julian.lbniwkalkulator.util.ErrorHandler.getActualCause;
import static com.julian.lbniwkalkulator.util.ErrorHandler.processException;
import static com.julian.lbniwkalkulator.util.ErrorHandler.showErrorDialog;

public class StartViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.start_view_layout);
            StringGetter.setAppContext(this);
        } catch (InflateException e) {
            Throwable actualCause = getActualCause(e);
            if (actualCause instanceof InvalidComponentException ex) {
                processException(this, ex.getMessage(), null, null, this::finish);
            }
        }
        setUpButtons();
        setUpLanguageSelector();
    }

    private void setUpButtons() {
        Button XRayPageButton = findViewById(R.id.button_select_xray);
        XRayPageButton.setOnClickListener(view -> {
            Intent intent = new Intent(StartViewActivity.this, XRayMenuViewActivity.class);
            startActivity(intent);
        });
        Button IsotopePageButton = findViewById(R.id.button_select_isotope);
        IsotopePageButton.setOnClickListener(view -> {
            Intent intent = new Intent(StartViewActivity.this, IsotopeMenuViewActivity.class);
            startActivity(intent);
        });
    }

    private void setUpLanguageSelector() {
        Button languageButton = findViewById(R.id.language_button);
        languageButton.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Settings.ACTION_APP_LOCALE_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                        showErrorDialog(StartViewActivity.this,
                                StringGetter.fromStringsXML(R.string.exception_couldnot_start_language_selector_title),
                                StringGetter.fromStringsXML(R.string.exception_coundnot_start_language_selector_message),
                                null);
            }
        });
    }
}


