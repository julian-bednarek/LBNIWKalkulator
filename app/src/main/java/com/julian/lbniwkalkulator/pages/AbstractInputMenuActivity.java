package com.julian.lbniwkalkulator.pages;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.julian.lbniwkalkulator.util.ErrorHandler.getActualCause;
import static com.julian.lbniwkalkulator.util.ErrorHandler.processException;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.components.InputFieldWrapper;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;

public abstract class AbstractInputMenuActivity extends AppCompatActivity {

    abstract protected RadiationData collectData();

    protected void setUpCalculateButton(int ID) {
        Log.d("KURWA", Integer.toString(ID));
        Button calculateButton = findViewById(ID);
        calculateButton.setOnClickListener(view -> {
            RadiationData data = collectData();
            Intent intent = new Intent(AbstractInputMenuActivity.this, CalculatedTimeViewActivity.class);
            intent.putExtra("input_data", data);
            startActivity(intent);
        });
        setUpHideKeyboard();
    }

    protected void setUpHideKeyboard() {
        findViewById(android.R.id.content).setOnClickListener(view -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            view.setSoundEffectsEnabled(false);
        });
    }

    protected void generalSetUp(int layoutID, int buttonID) {
        try {
            setContentView(layoutID);
        } catch (InflateException e) {
            Throwable actualCause = getActualCause(e);
            if (actualCause instanceof InvalidComponentException ex) {
                processException(this, ex.getMessage(), null, null, this::finish);
            }
        }
        setUpCalculateButton(buttonID);
    }
}
