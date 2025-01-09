package com.julian.lbniwkalkulator.core;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.julian.lbniwkalkulator.R;
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

    public SQLiteDatabaseWrapper getDatabase() {
        return database;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StringGetter.setAppContext(this);
        database = new SQLiteDatabaseWrapper(this);
        dbUpdateHandler.post(dbUpdater);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        database.close();
    }

}