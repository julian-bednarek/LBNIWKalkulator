package com.julian.lbniwkalkulator.util;

import android.content.Context;

public class StringGetter {
    private static Context appContext;

    public static void setAppContext(Context context) {
        appContext = context;
    }

    public static String fromStringsXML(int stringID) {
        return appContext.getString(stringID);
    }
}
