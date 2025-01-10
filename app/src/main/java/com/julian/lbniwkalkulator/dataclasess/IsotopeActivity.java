package com.julian.lbniwkalkulator.dataclasess;

import static android.provider.BaseColumns._ID;
import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.COLUMN_NAME_ACTIVITY;
import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.COLUMN_NAME_HALF_LIFE_TIME;
import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.COLUMN_NAME_ISOTOPE_NAME;
import static com.julian.lbniwkalkulator.localdata.IsotopeDatabaseContract.IsotopeEntry.COLUMN_NAME_MOST_RECENT_TIMESTAMP;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class IsotopeActivity implements Parcelable {
    private int ID;

    public void setActivity(double activity) {
        this.activity = activity;
    }

    public void setMostRecentTimestamp(long mostRecentTimestamp) {
        this.mostRecentTimestamp = mostRecentTimestamp;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setHalfLifeTime(double halfLifeTime) {
        this.halfLifeTime = halfLifeTime;
    }

    public void setIsotopeName(String isotopeName) {
        this.isotopeName = isotopeName;
    }

    private double activity;
    private long mostRecentTimestamp;
    private double halfLifeTime;
    private String isotopeName;

    public IsotopeActivity(int ID, String isotopeName, double halfLifeTime, long mostRecentTimestamp, double activity) {
        this.ID = ID;
        this.isotopeName = isotopeName;
        this.halfLifeTime = halfLifeTime;
        this.mostRecentTimestamp = mostRecentTimestamp;
        this.activity = activity;
    }

    public int getID() {
        return ID;
    }

    public double getActivity() {
        return activity;
    }

    public long getMostRecentTimestamp() {
        return mostRecentTimestamp;
    }

    public double getHalfLifeTime() {
        return halfLifeTime;
    }

    public String getIsotopeName() {
        return isotopeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeDouble(activity);
        dest.writeLong(mostRecentTimestamp);
        dest.writeDouble(halfLifeTime);
        dest.writeString(isotopeName);
    }

    protected IsotopeActivity(Parcel in) {
        ID = in.readInt();
        activity = in.readDouble();
        mostRecentTimestamp = in.readLong();
        halfLifeTime = in.readDouble();
        isotopeName = in.readString();
    }

    public static final Creator<IsotopeActivity> CREATOR = new Creator<IsotopeActivity>() {
        @Override
        public IsotopeActivity createFromParcel(Parcel in) {
            return new IsotopeActivity(in);
        }

        @Override
        public IsotopeActivity[] newArray(int size) {
            return new IsotopeActivity[size];
        }
    };

    public static IsotopeActivity fromDatabaseRecord(Cursor cursor) {
        return new IsotopeActivity(
                cursor.getInt(cursor.getColumnIndexOrThrow(_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_ISOTOPE_NAME)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_NAME_HALF_LIFE_TIME)),
                cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_MOST_RECENT_TIMESTAMP)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_NAME_ACTIVITY))
        );
    }

    public ContentValues toContentValues() {
        ContentValues retval = new ContentValues();
        retval.put(_ID, ID);
        retval.put(COLUMN_NAME_ISOTOPE_NAME, isotopeName);
        retval.put(COLUMN_NAME_HALF_LIFE_TIME, halfLifeTime);
        retval.put(COLUMN_NAME_MOST_RECENT_TIMESTAMP, mostRecentTimestamp);
        retval.put(COLUMN_NAME_ACTIVITY, activity);
        return retval;
    }
}
