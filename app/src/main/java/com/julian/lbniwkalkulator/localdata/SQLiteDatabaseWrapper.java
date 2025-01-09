package com.julian.lbniwkalkulator.localdata;

import static android.provider.BaseColumns._ID;
import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.julian.lbniwkalkulator.dataclasess.IsotopeActivity;
import com.julian.lbniwkalkulator.exceptions.InsertionFailedSQLiteException;

import java.util.ArrayList;
import java.util.Objects;

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

    public ArrayList<IsotopeActivity> loadRecords(String whereClause, String[] whereClauseArgs) {
        ArrayList<IsotopeActivity> retval = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            db.beginTransaction();
            Cursor recordCursor = this.select(whereClause, whereClauseArgs);
            if (recordCursor.moveToFirst()) {
                do {
                    retval.add(IsotopeActivity.fromDatabaseRecord(recordCursor));
                } while (recordCursor.moveToNext());
            }
            recordCursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Something went wrong in SQLite", Objects.requireNonNull(e.getMessage()));
        } finally {
            db.endTransaction();
        }
        return retval;
    }

    public void updateContents() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            Cursor recordCursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
            if (recordCursor.moveToFirst()) {
                do {
                    updateRow(db, recordCursor);
                } while (recordCursor.moveToNext());
            }
            recordCursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Something went wrong in SQLite", Objects.requireNonNull(e.getMessage()));
        } finally {
            db.endTransaction();
        }

    }

    private void updateRow(SQLiteDatabase db, Cursor recordCursor) {
        IsotopeActivity recordData = IsotopeActivity.fromDatabaseRecord(recordCursor);
        long currentTimestamp = System.currentTimeMillis() / 1_000;
        long timeDifference = currentTimestamp - recordData.getMostRecentTimestamp();
        recordData.setActivity(recordData.getActivity() * Math.pow(0.5, (timeDifference)/recordData.getHalfLifeTime()));
        recordData.setMostRecentTimestamp(currentTimestamp);
        ContentValues updatedRecord = recordData.toContentValues();
        db.update(TABLE_NAME, updatedRecord, String.format("%s = ?", _ID), new String[]{String.valueOf(recordData.getID())});
    }
}
