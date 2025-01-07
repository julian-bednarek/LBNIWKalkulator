package com.julian.lbniwkalkulator.localdata;

import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class SQLiteDatabaseWrapper {
    private static final String UPDATE_QUERY =
        """
        UPDATE isotopes
        SET mostRecentTimestamp = strftime('%s', 'now'),
        activity = activity * POW(0.5, CAST(strftime('%s', 'now') AS REAL) - mostRecentTimestamp) / (halfLifeTime * 60)
        WHERE mostRecentTimestamp < strftime('%s', 'now');
        """;
    private static final String DELETE_UPDATE_QUERY =
        """
        DELETE FROM isotopes
        WHERE activity < 0.01;
        """;

    private final IsotopeSQLiteHelper dbHelper;

    public SQLiteDatabaseWrapper(Context context) {
        this.dbHelper = new IsotopeSQLiteHelper(context);
    }

    public void close() {
        this.dbHelper.close();
    }

    public void insert(ContentValues values) {
        long chekcVal = dbHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
        if(chekcVal == -1) return; //TODO: Write and throw an exception
    }

    public Cursor select(String whereClause, String[] whereClauseArgs) {
        return dbHelper.getReadableDatabase().query(TABLE_NAME, null, whereClause, whereClauseArgs,
                null, null, null);
    }

    public void updateContents() {
        dbHelper.getWritableDatabase().execSQL(UPDATE_QUERY);
        dbHelper.getWritableDatabase().execSQL(DELETE_UPDATE_QUERY);
    }
}
