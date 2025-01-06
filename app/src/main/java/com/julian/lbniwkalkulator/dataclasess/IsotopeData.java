package com.julian.lbniwkalkulator.dataclasess;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class IsotopeData implements RadiationData {

    private final double activity;
    private final double materialThickness;
    private final int distanceToDetector;
    private final String isotopeType;
    private final int filmType;
    private final double targetDensity;

    @Override
    public String getType() {
        return RadiationDataTypes.ISOTOPE;
    }

    public IsotopeData(double activity, double materialThickness, int distanceToDetector, String isotopeType, int filmType, double targetDensity) {
        this.activity = activity;
        this.materialThickness = materialThickness;
        this.distanceToDetector = distanceToDetector;
        this.isotopeType = isotopeType;
        this.filmType = filmType;
        this.targetDensity = targetDensity;
    }

    public IsotopeData() {
        this.activity = 0;
        this.materialThickness = 0;
        this.distanceToDetector = 0;
        this.isotopeType = null;
        this.filmType = 0;
        this.targetDensity = 0;
    }

    public double getActivity() {
        return activity;
    }

    public double getMaterialThickness() {
        return materialThickness;
    }

    public int getDistanceToDetector() {
        return distanceToDetector;
    }

    public String getIsotopeType() {
        return isotopeType;
    }

    public int getFilmType() {
        return filmType;
    }

    public double getTargetDensity() {
        return targetDensity;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeDouble(activity);
        dest.writeDouble(materialThickness);
        dest.writeInt(distanceToDetector);
        dest.writeString(isotopeType);
        dest.writeInt(filmType);
        dest.writeDouble(targetDensity);
    }

    protected IsotopeData(Parcel in) {
        activity = in.readDouble();
        materialThickness = in.readDouble();
        distanceToDetector = in.readInt();
        isotopeType = in.readString();
        filmType = in.readInt();
        targetDensity = in.readDouble();
    }

    public static final Creator<IsotopeData> CREATOR = new Creator<IsotopeData>() {
        @Override
        public IsotopeData createFromParcel(Parcel in) {
            return new IsotopeData(in);
        }

        @Override
        public IsotopeData[] newArray(int size) {
            return new IsotopeData[size];
        }
    };
}
