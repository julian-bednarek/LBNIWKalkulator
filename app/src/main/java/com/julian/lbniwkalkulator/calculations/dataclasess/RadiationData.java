package com.julian.lbniwkalkulator.calculations.dataclasess;

import androidx.annotation.NonNull;

import java.io.Serializable;

public interface RadiationData extends Serializable{
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
}