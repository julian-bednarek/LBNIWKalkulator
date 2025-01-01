package com.julian.lbniwkalkulator.util;

import android.content.Context;

public class StringGetter {
    private static Context appContext;

    public static void setAppContext(Context context) {
        appContext = context;
    }

    public static String fromStringsXML(int stringID) {
        if(appContext == null) throw new NullPointerException("You didn't initialized appContext you moron");
        return appContext.getString(stringID);
    }
}
