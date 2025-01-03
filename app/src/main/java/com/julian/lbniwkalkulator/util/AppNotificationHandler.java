package com.julian.lbniwkalkulator.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.exceptions.NotificationManagerInitializationFailedException;

public class AppNotificationHandler {
    private static final String NOTIFICATION_CHANNEL_ID = "LBNIW_APP_CHANNEL";
    private static final CharSequence CHANNEL_NAME = "LBNIW_APP";
    private static final String CHANNEL_DESCRIPTION = "Notification channel of LBNIW Calculator app";
    private static final int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;
    private static final int NOTIFICATION_ID = 1;

    private final NotificationCompat.Builder builder;
    private final NotificationManager notificationManager;

    public AppNotificationHandler(Context context, ExposureTime exposureTime) throws NotificationManagerInitializationFailedException {
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);
        notificationChannel.setDescription(CHANNEL_DESCRIPTION);
        notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        long timeRemaining = exposureTime.toMilliseconds();
        this.builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        setUpBuilder(timeRemaining);
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) throw new NotificationManagerInitializationFailedException("Something went wrong during setting up notifications");
        this.notificationManager.createNotificationChannel(notificationChannel);
    }

    private void setUpBuilder(long timeRemaining) {
        this.builder.setSmallIcon(R.drawable.lbniw_ai)
                    .setContentTitle("Time left")
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setOngoing(timeRemaining != 0);

        if (timeRemaining > 0) {
            builder.setUsesChronometer(true)
                    .setChronometerCountDown(true)
                    .setShowWhen(true)
                    .setWhen(SystemClock.elapsedRealtime() + timeRemaining);
        } else {
            builder.setUsesChronometer(false)
                    .setShowWhen(false);
        }
    }

    public void cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
    }

    public void sendNotification() {
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void updateTimeRemaining(long timeRemaining) {
        builder.setWhen(System.currentTimeMillis() + timeRemaining);
    }
}
