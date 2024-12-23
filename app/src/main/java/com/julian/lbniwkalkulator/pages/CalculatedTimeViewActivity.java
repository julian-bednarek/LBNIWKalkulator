package com.julian.lbniwkalkulator.pages;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.RadiationDataProcessor;
import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationDataTypes;
import com.julian.lbniwkalkulator.calculations.dataclasess.XRayData;
import com.julian.lbniwkalkulator.exceptions.ExposureTimeTooLongException;
import com.julian.lbniwkalkulator.exceptions.InvalidRadiationDataTypeException;
import com.julian.lbniwkalkulator.exceptions.RadiationDataNotFoundException;

import java.util.Objects;

public class CalculatedTimeViewActivity extends AppCompatActivity {

    private static final String INPUT_DATA_INTENT = "input_data";
    long timeRemaining;
    boolean isCounting;
    CountDownTimer countDownTimer;
    ExposureTime exposureTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculated_time_view_layout);

        exposureTime = new ExposureTime();
        try {
            RadiationData data = getDataFromIntent();
            exposureTime = RadiationDataProcessor.processRadiationData(data);
        } catch (RadiationDataNotFoundException | ExposureTimeTooLongException |
                 InvalidRadiationDataTypeException e) {
            Log.e("Data error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        setUpButton();
    }

    private void setUpButton() {
        Button countDownButton = findViewById(R.id.count_down_button);
        timeRemaining = exposureTime.toMiliseconds();
        countDownButton.setText(exposureTime.toString());

        countDownButton.setOnClickListener(view -> {
            if (isCounting) {
                stopCountdown(countDownButton);
            } else {
                startCountdown(countDownButton);
            }
        });
    }

    private void startCountdown(Button countDownButton) {
        isCounting = true;

        countDownTimer = new CountDownTimer(timeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                countDownButton.setText(ExposureTime.fromMiliseconds(timeRemaining).toString());
            }

            @Override
            public void onFinish() {
                isCounting = false;
                countDownButton.setText(new ExposureTime().toString());
            }
        };
        countDownTimer.start();
    }

    private void stopCountdown(Button countDownButton) {
        isCounting = false;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeRemaining = exposureTime.toMiliseconds();
        countDownButton.setText(exposureTime.toString());
    }


    private RadiationData getDataFromIntent() throws RadiationDataNotFoundException {
        Intent intent = getIntent();
        if (intent == null) throw new RadiationDataNotFoundException("Something went wrong with radiation data");
        RadiationData retval = (RadiationData) intent.getSerializableExtra(INPUT_DATA_INTENT);
        if (retval == null) throw new RadiationDataNotFoundException("Something went wrong with radiation data");
        return retval;
    }

}
