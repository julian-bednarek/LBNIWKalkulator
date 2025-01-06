package com.julian.lbniwkalkulator.fragments;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.dataclasess.RadiationData;

public abstract class AbstractInputMenuFragment extends Fragment {

    public AbstractInputMenuFragment(int layout) {
        super(layout);
    }

    /**
     * Abstract method to collect data, to be implemented by subclasses.
     */
    protected abstract RadiationData collectData(View rootView);

    protected void setUpCalculateButton(@NonNull View view, int buttonID) {
        Button calculateButton = view.findViewById(buttonID);
        calculateButton.setOnClickListener(v -> {
            RadiationData data = collectData(view);
            Bundle bundle = new Bundle();
            bundle.putParcelable("input_data", data);
            CalculatedTimeViewFragment nextFragment = new CalculatedTimeViewFragment();
            nextFragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, nextFragment)
                    .addToBackStack(null)
                    .commit();
        });
        setUpHideKeyboard(view);
    }

    protected void setUpHideKeyboard(@NonNull View view) {
        view.setOnClickListener(v -> {
            InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            v.setSoundEffectsEnabled(false);
        });
    }

    protected void generalSetUp(View view, int buttonID) {

        setUpCalculateButton(view, buttonID);
    }
}

