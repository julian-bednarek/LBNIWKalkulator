package com.julian.lbniwkalkulator.pages;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.InflateException;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.RadiationDataProcessor;
import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.exceptions.ExposureTimeTooLongException;
import com.julian.lbniwkalkulator.exceptions.InvalidRadiationDataTypeException;
import com.julian.lbniwkalkulator.exceptions.NotificationManagerInitializationFailedException;
import com.julian.lbniwkalkulator.exceptions.RadiationDataNotFoundException;
import com.julian.lbniwkalkulator.util.AppNotificationHandler;

import java.util.Objects;

public class CalculatedTimeViewActivity extends AppCompatActivity {
    private static final String INPUT_DATA_INTENT = "input_data";

    private long timeRemaining;
    private boolean isCounting;
    private CountDownTimer countDownTimer;
    private ExposureTime exposureTime;
    private AppNotificationHandler notificationHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.calculated_time_view_layout);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        this.exposureTime = new ExposureTime();
        try {
            RadiationData data = getDataFromIntent();
            this.exposureTime = RadiationDataProcessor.processRadiationData(data);
        } catch (RadiationDataNotFoundException | ExposureTimeTooLongException |
                 InvalidRadiationDataTypeException e) {
            Log.e("Data error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        try {
            this.notificationHandler = new AppNotificationHandler(this, exposureTime);
        } catch (NotificationManagerInitializationFailedException e) {
            throw new RuntimeException(e);
        }
        setUpButton();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationHandler.cancelNotification();
    }

    private void setUpButton() {
        // TODO: Fix timing issues
        Button countDownButton = findViewById(R.id.count_down_button);
        timeRemaining = exposureTime.toMilliseconds();
        countDownButton.setText(exposureTime.toString());

        countDownButton.setOnClickListener(view -> {
            if (isCounting) {
                stopCountdown();
            } else {
                startCountdown(countDownButton);
                notificationHandler.updateTimeRemaining(timeRemaining);
                notificationHandler.sendNotification();
            }
        });
    }

    private void startCountdown(Button countDownButton) {
        isCounting = true;

        countDownTimer = new CountDownTimer(timeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                countDownButton.setText(ExposureTime.fromMilliseconds(timeRemaining).toString());
            }

            @Override
            public void onFinish() {
                isCounting = false;
                countDownButton.setText(new ExposureTime().toString());
                notificationHandler.cancelNotification();
            }
        };
        countDownTimer.start();
    }

    private void stopCountdown() {
        isCounting = false;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        notificationHandler.cancelNotification();
    }


    private RadiationData getDataFromIntent() throws RadiationDataNotFoundException {
        Intent intent = getIntent();
        if (intent == null) throw new RadiationDataNotFoundException("Something went wrong with radiation data");
        RadiationData retval = (RadiationData) intent.getSerializableExtra(INPUT_DATA_INTENT);
        if (retval == null) throw new RadiationDataNotFoundException("Something went wrong with radiation data");
        return retval;
    }

}
