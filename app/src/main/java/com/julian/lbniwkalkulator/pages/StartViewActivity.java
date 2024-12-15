package com.julian.lbniwkalkulator.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.InflateException;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;
public class StartViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.start_view_layout);
        } catch (InflateException e) {
            Throwable cause = e;
            Throwable actualCause = e.getCause();
            while (cause.getCause() != null) {
                actualCause = cause;
                cause = cause.getCause();
            }
            if (actualCause instanceof InvalidComponentException) {
                InvalidComponentException ex = (InvalidComponentException) actualCause;
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        }
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


