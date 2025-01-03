package com.julian.lbniwkalkulator.calculations.dataclasess;

import android.os.Parcelable;

import androidx.annotation.NonNull;


public interface RadiationData extends Parcelable {
    /**
     * Returns the type of radiation data.
     * E.g., "XRay" or "Isotope".
     */
    String getType();

    /**
     * For debug purposes.
     */
    @NonNull
    @Override
    String toString();
    
    @Override
    default int describeContents() {
        return 0;
    }
}