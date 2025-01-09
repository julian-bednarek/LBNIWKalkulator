package com.julian.lbniwkalkulator.localdata;

import static android.provider.BaseColumns._ID;
import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.COLUMN_NAME_ACTIVITY;
import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.COLUMN_NAME_HALF_LIFE_TIME;
import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.COLUMN_NAME_MOST_RECENT_TIMESTAMP;
import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.julian.lbniwkalkulator.dataclasess.IsotopeActivity;
import com.julian.lbniwkalkulator.exceptions.InsertionFailedSQLiteException;

public class SQLiteDatabaseWrapper {

    private final IsotopeSQLiteHelper dbHelper;

    public SQLiteDatabaseWrapper(Context context) {
        this.dbHelper = new IsotopeSQLiteHelper(context);
    }

    public void close() {
        this.dbHelper.close();
    }

    public void insert(ContentValues values) throws InsertionFailedSQLiteException{
        long checkVal = dbHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
        if(checkVal == -1) throw new InsertionFailedSQLiteException("Failed to insert values! To be localized", values);
    }

    public Cursor select(String whereClause, String[] whereClauseArgs) {
        return dbHelper.getReadableDatabase().query(TABLE_NAME, null, whereClause, whereClauseArgs,
                null, null, null);
    }

    public void updateContents() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            Cursor recordCursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
            if(recordCursor.moveToFirst()) {
                do {
                    updateRow(db, recordCursor);
                } while (recordCursor.moveToNext());
            }
            recordCursor.close();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }

    private void updateRow(SQLiteDatabase db, Cursor recordCursor) {
        IsotopeActivity recordData = IsotopeActivity.fromDatabaseRecord(recordCursor);
        long currentTimestamp = System.currentTimeMillis() / 1_000;
        long timeDifference = currentTimestamp - recordData.getMostRecentTimestamp();
        Log.d("XDD", String.valueOf(recordData.getActivity()));
        recordData.setActivity(recordData.getActivity() * Math.pow(0.5, (timeDifference)/recordData.getHalfLifeTime()));
        recordData.setMostRecentTimestamp(currentTimestamp);
        ContentValues updatedRecord = recordData.toContentValues();
        db.update(TABLE_NAME, updatedRecord, String.format("%s = ?", _ID), new String[]{String.valueOf(recordData.getID())});
    }
}
