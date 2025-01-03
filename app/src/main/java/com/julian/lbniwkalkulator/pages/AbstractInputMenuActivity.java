package com.julian.lbniwkalkulator.pages;

import static com.julian.lbniwkalkulator.util.ErrorHandler.getActualCause;
import static com.julian.lbniwkalkulator.util.ErrorHandler.processException;
import android.content.Context;
import android.content.Intent;
import android.view.InflateException;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentParameterException;
import com.julian.lbniwkalkulator.exceptions.MissingComponentParameterException;
import com.julian.lbniwkalkulator.util.StringGetter;

public abstract class AbstractInputMenuActivity extends AppCompatActivity {

    abstract protected RadiationData collectData();

    protected void setUpCalculateButton(int ID) {
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
            String message = e.getMessage();
            Integer additionalMessageId = null;
            String additionalMessage = null;
            if (actualCause instanceof MissingComponentParameterException ex) {
                message = ex.getMessage();
                additionalMessageId = R.string.exception_missing_component_parameter_exception_additional;
                additionalMessage = ex.getMissingParameter();
            } else if (actualCause instanceof InvalidComponentParameterException ex) {
                message = ex.getMessage();
                additionalMessageId = R.string.exception_invalid_component_parameter_exception_additional;
                additionalMessage = ex.getInvalidParameter();
            } else if (actualCause instanceof InvalidComponentException ex) {
                message = ex.getMessage();
            }
            processException(this, message, additionalMessageId, additionalMessage, this::finish);
        }
        setUpCalculateButton(buttonID);
    }
}
