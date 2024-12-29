package com.julian.lbniwkalkulator.util;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import com.julian.lbniwkalkulator.R;

public class ErrorHandler {

    public static Throwable getActualCause(Exception exception) {
        Throwable cause = exception;
        Throwable actualCause = exception.getCause();
        while (cause.getCause() != null) {
            actualCause = cause;
            cause = cause.getCause();
        }
        return actualCause;
    }

    public static void processException(Context context,
                                        String message,
                                        Integer additionalMessageID,
                                        Object additionalData,
                                        Runnable dismissAction) {
        String additionalMessage = additionalMessageID != null
                ? StringGetter.fromStringsXML(additionalMessageID)
                : "";
        String fullMessage = additionalData != null
                ? String.format("%s %s %s", message, additionalMessage, additionalData)
                : message;
        Log.e("DATA ERROR", fullMessage);
        showErrorDialog(context, StringGetter.fromStringsXML(R.string.error), fullMessage, dismissAction);
    }

    public static void showErrorDialog(Context context, String title, String message, Runnable dismissAction) {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("OK", (dialogInterface, which) -> {
                        dialogInterface.dismiss();
                    })
                    .setCancelable(false)
                    .create();

            dialog.setOnDismissListener(dialogInterface -> {
                if (dismissAction != null) {
                    dismissAction.run();
                }
            });

            dialog.show();
    }
}
