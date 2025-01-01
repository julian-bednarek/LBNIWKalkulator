package com.julian.lbniwkalkulator.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import java.util.Locale;

public class LanguageHandler {
    public static final String ENGLISH_LANG_CODE = "en";
    public static final String POLISH_LANG_CODE = "pl";
    private static String currentLanguage = ENGLISH_LANG_CODE;

    public static void setLanguage(@NonNull Context context, String languageCode) {
        if (languageCode.equalsIgnoreCase(currentLanguage)) {
            return;
        }

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new android.os.LocaleList(locale));
        Context updatedContext = context.createConfigurationContext(configuration);
        if (context instanceof Activity activity) {
            currentLanguage = languageCode;
            // TODO: Replace it with non-deprecated method
            activity
                    .getBaseContext()
                    .getResources()
                    .updateConfiguration(
                            configuration,
                            updatedContext.getResources().getDisplayMetrics());
            activity.recreate();
        }
    }
}