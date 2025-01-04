package com.julian.lbniwkalkulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import android.Manifest;
import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.exceptions.NotificationManagerInitializationFailedException;
import com.julian.lbniwkalkulator.pages.StartViewActivity;
import com.julian.lbniwkalkulator.util.AppNotificationHandler;
import com.julian.lbniwkalkulator.util.StringGetter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NotificationHandlerTests {

    private Context context;
    private AppNotificationHandler appNotificationHandler;
    private ActivityScenario<StartViewActivity> scenario;

    @Before
    public void setUp() throws NotificationManagerInitializationFailedException, InputNotSupportedException {
        context = ApplicationProvider.getApplicationContext();
        StringGetter.setAppContext(context);
        scenario = ActivityScenario.launch(StartViewActivity.class);
        ExposureTime exposureTime = ExposureTime.fromMilliseconds(60_000);
        appNotificationHandler = new AppNotificationHandler(context, exposureTime);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                InstrumentationRegistry.getInstrumentation().getUiAutomation().grantRuntimePermission(
                        context.getPackageName(),
                        Manifest.permission.POST_NOTIFICATIONS
                );
            }
        }
    }

    @Test
    public void testSendNotification() {
        appNotificationHandler.sendNotification();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assertNotNull("NotificationManager should not be null", notificationManager);
        assertEquals("There should be at least one active notification", 1, notificationManager.getActiveNotifications().length);
    }

    @Test
    public void testCancelNotification() {
        scenario.onActivity(activity -> {
            appNotificationHandler.sendNotification();
            appNotificationHandler.cancelNotification();
        });
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assertNotNull("NotificationManager should not be null", notificationManager);
        assertEquals("All notifications should be canceled", 0, notificationManager.getActiveNotifications().length);
    }
}

