package com.julian.lbniwkalkulator.core;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.julian.lbniwkalkulator.localdata.IsotopeSQLiteHelper;
import com.julian.lbniwkalkulator.localdata.SQLiteDatabaseWrapper;
import com.julian.lbniwkalkulator.util.StringGetter;

public class LBNIWKalkulatorApp extends Application {
    private SQLiteDatabaseWrapper database;
    //TODO: Add thread safe database update every 3 minutes

    @Override
    public void onCreate() {
        super.onCreate();
        StringGetter.setAppContext(this);
        database = new SQLiteDatabaseWrapper(this);

    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        database.close();
    }
}
