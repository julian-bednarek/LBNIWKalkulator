package com.julian.lbniwkalkulator.core;

import static android.provider.BaseColumns._ID;

import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.COLUMN_NAME_ACTIVITY;
import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.COLUMN_NAME_HALF_LIFE_TIME;
import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.COLUMN_NAME_ISOTOPE_NAME;
import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.COLUMN_NAME_MOST_RECENT_TIMESTAMP;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.exceptions.InsertionFailedSQLiteException;
import com.julian.lbniwkalkulator.localdata.SQLiteDatabaseWrapper;
import com.julian.lbniwkalkulator.util.StringGetter;

public class MainActivity extends AppCompatActivity {
    private static final int AUTO_UPDATE_MILLISECONDS = 1_000;

    private SQLiteDatabaseWrapper database;
    private final Handler dbUpdateHandler = new Handler(Looper.getMainLooper());
    private final Runnable dbUpdater = new Runnable() {
        @Override
        public void run() {
            try {
                database.updateContents();
            } catch (Exception ignored) {
            } finally {
                dbUpdateHandler.postDelayed(this, AUTO_UPDATE_MILLISECONDS);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StringGetter.setAppContext(this);
        database = new SQLiteDatabaseWrapper(this);
        dbUpdateHandler.post(dbUpdater);
        ContentValues test = new ContentValues();
        test.put(_ID, String.valueOf(1));
        test.put(COLUMN_NAME_ACTIVITY, String.valueOf(65.56));
        test.put(COLUMN_NAME_HALF_LIFE_TIME, String.valueOf(1234));
        test.put(COLUMN_NAME_ISOTOPE_NAME, "C-69");
        test.put(COLUMN_NAME_MOST_RECENT_TIMESTAMP, String.valueOf(System.currentTimeMillis() / 1_000));
        try {
            database.insert(test);
        } catch (InsertionFailedSQLiteException e) {
            Log.d("LOL", "LOL");
        }
        DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        database.close();
    }

}