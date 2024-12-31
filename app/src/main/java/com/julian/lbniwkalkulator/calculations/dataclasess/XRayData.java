package com.julian.lbniwkalkulator.calculations.dataclasess;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class XRayData implements RadiationData {

    private final int voltage;
    private final int current;
    private final double steelThickness;
    private final int filmType;
    private final double sourceToDetectorDistance;
    private final double targetDensity;

    public XRayData(int voltage, int current, double steelThickness, int filmType, double sourceToDetectorDistance, double targetDensity) {
        this.voltage = voltage;
        this.current = current;
        this.steelThickness = steelThickness;
        this.filmType = filmType;
        this.targetDensity = targetDensity;
        this.sourceToDetectorDistance = sourceToDetectorDistance;
    }

    public XRayData() {
        this.voltage = 0;
        this.current = 0;
        this.steelThickness = 0;
        this.filmType = 0;
        this.targetDensity = 0;
        this.sourceToDetectorDistance = 0;
    }

    @Override
    public String getType() {
        return RadiationDataTypes.XRAY;
    }

    /**
     * For debug purpose only
     */
    @NonNull
    @Override
    public String toString() {
        return "XRayData{" +
                "voltage=" + voltage +
                ", current=" + current +
                ", steelThickness=" + steelThickness +
                ", filmType=" + filmType +
                ", sourceToDetectorDistance=" + sourceToDetectorDistance +
                ", targetDensity=" + targetDensity +
                '}';
    }

    public int getVoltage() {
        return voltage;
    }

    public int getCurrent() {
        return current;
    }

    public double getSteelThickness() {
        return steelThickness;
    }

    public int getFilmType() {
        return filmType;
    }

    public double getSourceToDetectorDistance() {
        return sourceToDetectorDistance;
    }

    public double getTargetDensity() {
        return targetDensity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(voltage);
        dest.writeInt(current);
        dest.writeDouble(steelThickness);
        dest.writeInt(filmType);
        dest.writeDouble(sourceToDetectorDistance);
        dest.writeDouble(targetDensity);
    }

    protected XRayData(Parcel in) {
        voltage = in.readInt();
        current = in.readInt();
        steelThickness = in.readDouble();
        filmType = in.readInt();
        sourceToDetectorDistance = in.readDouble();
        targetDensity = in.readDouble();
    }

    public static final Creator<XRayData> CREATOR = new Creator<XRayData>() {
        @Override
        public XRayData createFromParcel(Parcel in) {
            return new XRayData(in);
        }

        @Override
        public XRayData[] newArray(int size) {
            return new XRayData[size];
        }
    };
}
