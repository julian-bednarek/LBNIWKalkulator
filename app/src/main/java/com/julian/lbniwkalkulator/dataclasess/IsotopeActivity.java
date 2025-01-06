package com.julian.lbniwkalkulator.dataclasess;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class IsotopeActivity implements Parcelable {
    private final double activity;
    private final long mostRecentTimestamp;
    private final double halfLifeTime;
    private final String isotopeName;

    public IsotopeActivity(String isotopeName, double halfLifeTime, long mostRecentTimestamp, double activity) {
        this.isotopeName = isotopeName;
        this.halfLifeTime = halfLifeTime;
        this.mostRecentTimestamp = mostRecentTimestamp;
        this.activity = activity;
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
        dest.writeDouble(activity);
        dest.writeLong(mostRecentTimestamp);
        dest.writeDouble(halfLifeTime);
        dest.writeString(isotopeName);
    }

    protected IsotopeActivity(Parcel in) {
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
}
