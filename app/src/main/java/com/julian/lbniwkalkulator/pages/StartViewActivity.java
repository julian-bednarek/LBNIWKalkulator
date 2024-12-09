package com.julian.lbniwkalkulator.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.julian.lbniwkalkulator.R;

public class StartViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_view_layout);
    }

    public void goToIsotopePage(View view) {
        Intent intent = new Intent(StartViewActivity.this, IsotopeMenuViewActivity.class);
        startActivity(intent);
    }

    public void goToXRayPage(View view) {
        Intent intent = new Intent(StartViewActivity.this, XRayMenuViewActivity.class);
        startActivity(intent);
    }
}


