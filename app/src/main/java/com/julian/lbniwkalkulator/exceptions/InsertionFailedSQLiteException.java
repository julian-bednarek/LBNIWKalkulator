package com.julian.lbniwkalkulator.exceptions;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;

public class InsertionFailedSQLiteException extends SQLiteException {
    ContentValues valuesFailedToInsert;

    public InsertionFailedSQLiteException(String message) {
        super(message);
        this.valuesFailedToInsert = null;
    }

    public InsertionFailedSQLiteException(String message, ContentValues values) {
        super(message);
        this.valuesFailedToInsert = values;
    }
}
