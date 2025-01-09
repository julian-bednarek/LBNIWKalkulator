package com.julian.lbniwkalkulator.localdata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.*;

import androidx.annotation.Nullable;

public class IsotopeSQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "isotopes.db";

    private static final String SQL_CREATE_ENTRIES = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s REAL, %s INTEGER, %s REAL)",
            IsotopeEntry.TABLE_NAME,
            IsotopeEntry._ID,
            IsotopeEntry.COLUMN_NAME_ISOTOPE_NAME,
            IsotopeEntry.COLUMN_NAME_ACTIVITY,
            IsotopeEntry.COLUMN_NAME_MOST_RECENT_TIMESTAMP,
            IsotopeEntry.COLUMN_NAME_HALF_LIFE_TIME
    );


    public IsotopeSQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("UNSUPPORTED UPGRADE OF DATABASE", "This database was not meant to be upgraded");
        throw new UnsupportedOperationException("This database is not meant to be upgraded!");
    }
}
