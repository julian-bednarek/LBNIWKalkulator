package com.julian.lbniwkalkulator.calculations;


import com.julian.lbniwkalkulator.enums.FilmTypeEnum;

import java.io.Serializable;

public class XRayData implements Serializable {

    private int voltage;
    private int current;
    private double steelThickness;
    private int filmType;
    private double sourceToDetectorDistance;
    private double targetDensity;

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

    /**
     * For debug purpose only
     */
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

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public void setSteelThickness(double steelThickness) {
        this.steelThickness = steelThickness;
    }

    public void setFilmType(int filmType) {
        this.filmType = filmType;
    }

    public void setSourceToDetectorDistance(double sourceToDetectorDistance) {
        this.sourceToDetectorDistance = sourceToDetectorDistance;
    }

    public void setTargetDensity(double targetDensity) {
        this.targetDensity = targetDensity;
    }

    public double getTargetDensity() {
        return targetDensity;
    }
}
