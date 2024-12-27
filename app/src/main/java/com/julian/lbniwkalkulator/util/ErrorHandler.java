package com.julian.lbniwkalkulator.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.julian.lbniwkalkulator.R;

import java.util.Objects;

public class ErrorHandler {
    public static void processException(Context context,String message, Integer additionalMessageID, Object additionalData) {
        String additionalMessage = additionalMessageID != null
                ? StringGetter.fromStringsXML(additionalMessageID)
                : "";
        String fullMessage = additionalData != null
                ? String.format("%s %s %s", message, additionalMessage, additionalData)
                : message;
        Log.e("DATA ERROR", fullMessage);
        showErrorDialog(context, StringGetter.fromStringsXML(R.string.error), fullMessage);
    }

    public static void showErrorDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
