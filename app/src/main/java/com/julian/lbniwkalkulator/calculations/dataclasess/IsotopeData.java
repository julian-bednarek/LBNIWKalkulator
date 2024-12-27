package com.julian.lbniwkalkulator.calculations.dataclasess;

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
        this.isotopeType = null;
        this.distanceToDetector = 0;
        this.filmType = 0;
        this.targetDensity = 0;
        this.materialThickness = 0;
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
}
