package com.julian.lbniwkalkulator.calculations.dataclasess;

import androidx.annotation.NonNull;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.util.StringGetter;

import java.util.Locale;

public class ExposureTime {
    private final int minutes;
    private final int seconds;
    private final int hundredthsOfSecond;

    private ExposureTime(int minutes, int seconds, int hundredthsOfSecond) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.hundredthsOfSecond = hundredthsOfSecond;
    }

    public ExposureTime() {
        this.minutes = 0;
        this.seconds = 0;
        this.hundredthsOfSecond = 0;
    }

    public static ExposureTime fromMilliseconds(long timeInMS) throws InputNotSupportedException {
        if(timeInMS < 0) throw new InputNotSupportedException(StringGetter.fromStringsXML(R.string.exception_input_not_supported_message));
        return new ExposureTime((int) (timeInMS / 60_000),
                (int) ((timeInMS / 1000) % 60),
                (int) (timeInMS % 1_000) / 10);
    }

    public long toMilliseconds() {
        return (60L * minutes + seconds ) * 1_000 + hundredthsOfSecond * 10L;
    }

    public int getMinutes() {
        return minutes;
    }
    public int getSeconds() {
        return seconds;
    }
    public int getHundredthsOfSecond() {
        return hundredthsOfSecond;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US,"%02d:%02d.%02d", minutes, seconds, hundredthsOfSecond);
    }
}
