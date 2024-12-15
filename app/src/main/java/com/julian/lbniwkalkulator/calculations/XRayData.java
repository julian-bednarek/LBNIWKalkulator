package com.julian.lbniwkalkulator.calculations;


import java.io.Serializable;

public class XRayData implements Serializable {

    private int voltage;
    private int current;
    private double steelThickness;
    private FilmTypeEnum filmType;
    private double sourceToDetectorDistance;
    private double targetDensity;

    public XRayData(int voltage, int current, double steelThickness, FilmTypeEnum filmType, double sourceToDetectorDistance, double targetDensity) {
        this.voltage = voltage;
        this.current = current;
        this.steelThickness = steelThickness;
        this.filmType = filmType;
        this.targetDensity = targetDensity;
        this.sourceToDetectorDistance = sourceToDetectorDistance;
    }

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

    public FilmTypeEnum getFilmType() {
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

    public void setFilmType(FilmTypeEnum filmType) {
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
