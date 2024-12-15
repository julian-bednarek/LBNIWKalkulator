package com.julian.lbniwkalkulator.pages;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.julian.lbniwkalkulator.calculations.XRayData;

public class CalculatedTimeViewAcitivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = ((XRayData)getIntent().getSerializableExtra("x_ray_input_data")).toString();
        Log.d("DUPA", data);
    }
}
