package com.julian.lbniwkalkulator.util;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.components.dialogs.CustomErrorDialog;

public class ErrorHandler {

    public static Throwable getActualCause(Exception exception) {
        Throwable actualCause = exception;
        while (actualCause.getCause() != null) {
            actualCause = actualCause.getCause();
        }
        return actualCause;
    }

    public static void processException(Context context,
                                        String message,
                                        Integer additionalMessageID,
                                        Object additionalData,
                                        Runnable dismissAction) {
        String additionalMessage = additionalMessageID != null && additionalData != null
                ? StringGetter.fromStringsXML(additionalMessageID)
                : "";
        String fullMessage = additionalData != null
                ? String.format("%s %s %s", message, additionalMessage, additionalData)
                : message;
        showErrorDialog(context, StringGetter.fromStringsXML(R.string.error), fullMessage, dismissAction);
    }

    public static final String ERROR_BUTTON_TEXT = "OK";

    public static void showErrorDialog(Context context, String title, String message, Runnable dismissAction) {
            CustomErrorDialog dialog = new CustomErrorDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setDismissButtonText(ERROR_BUTTON_TEXT)
                    .create();

            dialog.setOnDismissListener(dialogInterface -> {
                if (dismissAction != null) {
                    dismissAction.run();
                }
            });
            dialog.show();
    }
}
