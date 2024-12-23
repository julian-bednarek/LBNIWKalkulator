package com.julian.lbniwkalkulator.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.RadiationDataProcessor;
import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.exceptions.ExposureTimeTooLongException;
import com.julian.lbniwkalkulator.exceptions.InvalidRadiationDataTypeException;
import com.julian.lbniwkalkulator.exceptions.RadiationDataNotFoundException;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import java.util.Objects;

public class CalculatedTimeViewActivity extends AppCompatActivity {
    private static final String NOTIFICATION_CHANNEL_ID = "LBNIW_APP_CHANNEL";
    private static final String INPUT_DATA_INTENT = "input_data";
    private NotificationCompat.Builder builder;
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
        createNotificationChannel();
        setUpButton();
    }

    private void createNotificationChannel() {
        final CharSequence channelName = "LBNIW_APP";
        final String channelDescription = "Notification channel of LBNIW Calculator app";
        int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel appChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, channelImportance);
        appChannel.setDescription(channelDescription);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(appChannel);
    }

    private void sendNotification() {
        if (builder == null) {
            builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.lbniw_ai)
                    .setContentTitle("Time left")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_ALARM);
        }

        builder.setContentText(ExposureTime.fromMilliseconds(timeRemaining).toString());

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final int NOTIFICATION_ID = 1;
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private void cancelNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final int NOTIFICATION_ID = 1;
        if (notificationManager != null) {
            notificationManager.cancel(NOTIFICATION_ID);
        }
    }

    private void setUpButton() {
        Button countDownButton = findViewById(R.id.count_down_button);
        timeRemaining = exposureTime.toMilliseconds();
        countDownButton.setText(exposureTime.toString());

        countDownButton.setOnClickListener(view -> {
            if (isCounting) {
                stopCountdown(countDownButton);
            } else {
                startCountdown(countDownButton);
                sendNotification();
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
                sendNotification();
            }

            @Override
            public void onFinish() {
                isCounting = false;
                countDownButton.setText(new ExposureTime().toString());
                cancelNotification();
            }
        };
        countDownTimer.start();
    }

    private void stopCountdown(Button countDownButton) {
        isCounting = false;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeRemaining = exposureTime.toMilliseconds();
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
