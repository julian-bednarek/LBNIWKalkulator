package com.julian.lbniwkalkulator.core;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.databinding.MainActivityBinding;
import com.julian.lbniwkalkulator.fragments.StartViewFragment;
import com.julian.lbniwkalkulator.fragments.StartViewFragmentDirections;
import com.julian.lbniwkalkulator.util.StringGetter;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
    }
}