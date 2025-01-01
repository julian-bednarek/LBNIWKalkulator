package com.julian.lbniwkalkulator.pages;

import static android.Manifest.permission.POST_NOTIFICATIONS;
import static com.julian.lbniwkalkulator.util.ErrorHandler.getActualCause;
import static com.julian.lbniwkalkulator.util.ErrorHandler.processException;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.InflateException;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.RadiationDataProcessor;
import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.exceptions.ExposureTimeTooLongException;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;
import com.julian.lbniwkalkulator.exceptions.InvalidRadiationDataTypeException;
import com.julian.lbniwkalkulator.exceptions.RadiationDataNotFoundException;
import com.julian.lbniwkalkulator.util.AppNotificationHandler;
import com.julian.lbniwkalkulator.util.AudioHandler;

public class CalculatedTimeViewActivity extends AppCompatActivity {
    private static final String INPUT_DATA_INTENT = "input_data";
    private static final int SOUND_TIME_MILLISECONDS = 10_000;
    private static final int TIMER_TICK_MILLISECONDS = 100;
    private static final int POST_NOTIFICATION_PERMISSION_REQUEST_CODE = 1;

    private boolean sendNotifications = false;
    private long timeRemaining;
    private boolean isCounting;
    private CountDownTimer countDownTimer;
    private ExposureTime exposureTime;
    private AppNotificationHandler notificationHandler;
    private AudioHandler audioHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.calculated_time_view_layout);
        } catch (InflateException e) {
            Throwable actualCause = getActualCause(e);
            if (actualCause instanceof InvalidComponentException ex) {
                processException(this, ex.getMessage(), null, null, this::finish);
            }
        }
        setExposureTime();
        audioHandler = new AudioHandler(this);
        this.notificationHandler = new AppNotificationHandler(this, exposureTime);
        setUpButton();
        handlePermissions();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelSoundAndNotification();
    }

    private void handlePermissions() {
        if(ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    POST_NOTIFICATION_PERMISSION_REQUEST_CODE);
        } else {
            sendNotifications = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == POST_NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendNotifications = true;
            } else {
                sendNotifications = false;
                Toast.makeText(this, "Permission to post notifications denied.",
                        Toast.LENGTH_SHORT).show();
            }
        }
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
                    e.getExposureTime(),
                    this::finish);
        } catch (RadiationDataNotFoundException | InputNotSupportedException e) {
            processException(this, e.getMessage(), null, null, this::finish);
        } catch (InvalidRadiationDataTypeException e) {
            processException(this,
                    e.getMessage(),
                    R.string.exception_invalid_radiation_data_type_additional,
                    e.getInvalidType(),
                    this::finish);
        }
    }

    private void setUpButton() {
        Button countDownButton = findViewById(R.id.count_down_button);
        timeRemaining = exposureTime.toMilliseconds();
        countDownButton.setText(exposureTime.toString());
        countDownButton.setOnClickListener(view -> {
            if (isCounting) {
                stopCountdown();
            } else {
                startCountdown(countDownButton);
            }
        });
    }

    private void sendNotificationsConditionally() {
        if(sendNotifications) {
            notificationHandler.updateTimeRemaining(timeRemaining);
            notificationHandler.sendNotification();
        }
    }

    private void startCountdown(Button countDownButton) {
        isCounting = true;
        if(timeRemaining == 0) return;
        sendNotificationsConditionally();
        setUpCountDownTimer(countDownButton);
    }

    private void setUpCountDownTimer(Button countDownButton) {
        final boolean[] soundMade = {false};
        countDownTimer = new CountDownTimer(timeRemaining, TIMER_TICK_MILLISECONDS) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                if(millisUntilFinished <= SOUND_TIME_MILLISECONDS && !soundMade[0]) {
                    soundMade[0] = true;
                    audioHandler.play(R.raw.warning_music);
                }
                try {
                    countDownButton.setText(ExposureTime.fromMilliseconds(timeRemaining).toString());
                } catch (InputNotSupportedException e) {
                    throw new RuntimeException("Something really bad happened in count down timer", e);
                }
            }
            @Override
            public void onFinish() {
                isCounting = false;
                countDownButton.setText(new ExposureTime().toString());
                cancelSoundAndNotification();
            }
        }.start();
    }

    private void cancelSoundAndNotification() {
        notificationHandler.cancelNotification();
        audioHandler.stop();
    }

    private void stopCountdown() {
        isCounting = false;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        cancelSoundAndNotification();
    }


    @NonNull
    private RadiationData getDataFromIntent() throws RadiationDataNotFoundException {
        Intent intent = getIntent();
        if (intent == null) throw new RadiationDataNotFoundException("Something went wrong with radiation data");
        RadiationData retval = intent.getParcelableExtra(INPUT_DATA_INTENT, RadiationData.class);
        if (retval == null) throw new RadiationDataNotFoundException("Something went wrong with radiation data");
        return retval;
    }

}
