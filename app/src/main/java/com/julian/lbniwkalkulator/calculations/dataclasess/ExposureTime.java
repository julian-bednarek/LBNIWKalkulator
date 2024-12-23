package com.julian.lbniwkalkulator.calculations.dataclasess;

public class ExposureTime {
    private int minutes;
    private int seconds;

    public ExposureTime(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return Integer.toString(minutes) + ":" + Integer.toString(seconds);
    }
}
