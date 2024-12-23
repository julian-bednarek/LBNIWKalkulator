package com.julian.lbniwkalkulator.calculations.dataclasess;

import java.util.Locale;

public class ExposureTime {
    private int minutes;
    private int seconds;

    public ExposureTime(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public ExposureTime() {
        this.minutes = 0;
        this.seconds = 0;
    }

    public static ExposureTime fromMiliseconds(long timeInMS) {
        return new ExposureTime((int) (timeInMS / 60_000), (int) ((timeInMS / 1000) % 60));
    }

    public long toMiliseconds() {
        return (60L * minutes + seconds ) * 1_000;
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
        return String.format(Locale.US,"%02d:%02d", minutes, seconds);
    }
}
