package com.julian.lbniwkalkulator.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.calculations.dataclasess.RadiationDataTypes;
import com.julian.lbniwkalkulator.calculations.dataclasess.XRayData;

public class CalculatedTimeViewActivity extends AppCompatActivity {

    private static final String INPUT_DATA_INTENT = "input_data";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculated_time_view_layout);

        RadiationData data = getDataFromIntent();
        if (data == null) {
            throw new IllegalStateException("No valid RadiationData provided in intent");
        }
    }

    private RadiationData getDataFromIntent() {
        Intent intent = getIntent();
        if (intent == null) return null;
        return (RadiationData) intent.getSerializableExtra(INPUT_DATA_INTENT);
    }

}
