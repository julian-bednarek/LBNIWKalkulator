package com.julian.lbniwkalkulator.pages;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.XRayData;

public class CalculatedTimeViewActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculated_time_view_layout);
        String data = ((XRayData)getIntent().getSerializableExtra("x_ray_input_data")).toString();
        TextView test = findViewById(R.id.test_text);
        test.setText(data);
        Log.d("DUPA", data);
    }

}
