package com.julian.lbniwkalkulator.pages;

import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentException;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentParameterException;

import java.util.Arrays;
import java.util.Objects;

public class XRayMenuViewActivity extends AppCompatActivity {

    /**
     * In catch clause, cause chain is expected to be:
     * InflateException -> InvocationTargetException -> InvalidComponentException or its subclasses
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.x_ray_menu_layout);
        } catch (InflateException e) {
            Throwable actualCause = Objects.requireNonNull(Objects.requireNonNull(e.getCause()).getCause()).getCause();
            if (actualCause instanceof InvalidComponentException) {
                InvalidComponentException ex = (InvalidComponentException) actualCause;
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
