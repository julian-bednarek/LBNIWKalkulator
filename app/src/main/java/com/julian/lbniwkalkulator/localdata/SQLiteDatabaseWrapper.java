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
import java.util.List;
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

    public void delete(String whereClause, String[] whereClauseArgs) {
        dbHelper.getWritableDatabase().delete(TABLE_NAME, whereClause, whereClauseArgs);
    }

    public static class DeleteWhereClause {
        private final String whereClause;
        private final String[] whereClauseArgs;

        public DeleteWhereClause(List<Integer> IDsToRemove) {
            this.whereClauseArgs = IDsToRemove.stream()
                    .map(String::valueOf)
                    .toArray(String[]::new);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("%s IN (", _ID));
            for (int i = 0; i < IDsToRemove.size(); i++) {
                stringBuilder.append("?");
                if (i < IDsToRemove.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(")");
            this.whereClause = stringBuilder.toString();
        }

        public String getWhereClause() {
            return whereClause;
        }

        public String[] getWhereClauseArgs() {
            return whereClauseArgs;
        }
    }

    public ArrayList<IsotopeActivity> loadAllRecords() {
        return loadRecords(null,null);
    }

    public int getUsableID() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int usableID = 1;
        try (Cursor cursor = db.rawQuery("SELECT MAX(" + _ID + ") FROM " + TABLE_NAME, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                usableID = cursor.getInt(0) + 1;
            }
        } catch (Exception e) {
            Log.e("SQLiteDatabaseWrapper", "Error getting usable ID: " + e.getMessage());
        }
        return usableID;
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
