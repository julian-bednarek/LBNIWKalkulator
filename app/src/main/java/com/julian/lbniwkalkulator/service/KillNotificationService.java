package com.julian.lbniwkalkulator.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class KillNotificationService extends Service{

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        String notificationService = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getSystemService(notificationService);
        nMgr.cancelAll();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}