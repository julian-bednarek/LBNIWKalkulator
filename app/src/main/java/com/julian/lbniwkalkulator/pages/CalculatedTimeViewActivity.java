package com.julian.lbniwkalkulator.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.RadiationDataProcessor;
import com.julian.lbniwkalkulator.calculations.dataclasess.ExposureTime;
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
        ExposureTime time = RadiationDataProcessor.processRadiationData(data);
        setUpButton(time);
    }

    private void setUpButton(ExposureTime time) {
        Button countDownButton = findViewById(R.id.count_down_button);
        countDownButton.setText(time.toString());
    }

    private RadiationData getDataFromIntent() {
        Intent intent = getIntent();
        if (intent == null) return null;
        return (RadiationData) intent.getSerializableExtra(INPUT_DATA_INTENT);
    }

}
