package com.julian.lbniwkalkulator.pages;

import static com.julian.lbniwkalkulator.util.ErrorHandler.getActualCause;
import static com.julian.lbniwkalkulator.util.ErrorHandler.processException;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.InflateException;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.RadiationDataProcessor;
import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.exceptions.ExposureTimeTooLongException;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;
import com.julian.lbniwkalkulator.exceptions.InvalidRadiationDataTypeException;
import com.julian.lbniwkalkulator.exceptions.NotificationManagerInitializationFailedException;
import com.julian.lbniwkalkulator.exceptions.RadiationDataNotFoundException;
import com.julian.lbniwkalkulator.util.AppNotificationHandler;

public class CalculatedTimeViewActivity extends AppCompatActivity {
    private static final String INPUT_DATA_INTENT = "input_data";
    private static final int SOUND_TIME_MILLISECONDS = 10_000;
    private static final int TIMER_TICK_MILLISECONDS = 1_000;


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
        } catch (InflateException e) {
            Throwable actualCause = getActualCause(e);
            if (actualCause instanceof InvalidComponentException) {
                InvalidComponentException ex = (InvalidComponentException) actualCause;
                processException(this, ex.getMessage(), null, null);
                finish();
            }
        }
        setExposureTime();
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


    private void setExposureTime() {
        this.exposureTime = new ExposureTime();
        try {
            RadiationData data = getDataFromIntent();
            this.exposureTime = RadiationDataProcessor.processRadiationData(data);
        } catch (ExposureTimeTooLongException e) {
            processException(this,
                    e.getMessage(),
                    R.string.exception_too_long_exposure_time_additional,
                    e.getExposureTime());
            finish();
        } catch (RadiationDataNotFoundException | InputNotSupportedException e) {
            processException(this, e.getMessage(), null, null);
            finish();
        } catch (InvalidRadiationDataTypeException e) {
            processException(this,
                    e.getMessage(),
                    R.string.exception_invalid_radiation_data_type_additional,
                    e.getInvalidType());
            finish();
        }
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
        final boolean[] soundMade = {false};
        countDownTimer = new CountDownTimer(timeRemaining, TIMER_TICK_MILLISECONDS) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                if(millisUntilFinished <= SOUND_TIME_MILLISECONDS && !soundMade[0]) {
                    soundMade[0] = true;
                }
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


    @NonNull
    private RadiationData getDataFromIntent() throws RadiationDataNotFoundException {
        Intent intent = getIntent();
        if (intent == null) throw new RadiationDataNotFoundException("Something went wrong with radiation data");
        RadiationData retval = (RadiationData) intent.getSerializableExtra(INPUT_DATA_INTENT);
        if (retval == null) throw new RadiationDataNotFoundException("Something went wrong with radiation data");
        return retval;
    }

}
