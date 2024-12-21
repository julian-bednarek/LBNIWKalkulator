package com.julian.lbniwkalkulator.pages;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.julian.lbniwkalkulator.R;

public class CalculatedTimeViewActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculated_time_view_layout);
        //((XRayData)getIntent().getSerializableExtra("x_ray_input_data"))
    }

}
