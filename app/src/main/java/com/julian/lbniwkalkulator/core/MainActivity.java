package com.julian.lbniwkalkulator.core;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.databinding.MainActivityBinding;
import com.julian.lbniwkalkulator.fragments.StartViewFragment;
import com.julian.lbniwkalkulator.util.StringGetter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentContainerView, StartViewFragment.class, null)
                    .commit();
        }
    }
}