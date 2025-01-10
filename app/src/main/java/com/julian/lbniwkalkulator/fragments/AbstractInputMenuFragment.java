package com.julian.lbniwkalkulator.fragments;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import com.julian.lbniwkalkulator.dataclasess.RadiationData;

/**
 * Abstract class for input fragments <b>mandatory</b> if the next fragment is meant to {@link CalculatedTimeViewFragment}
 */
public abstract class AbstractInputMenuFragment extends Fragment {

    public AbstractInputMenuFragment(int layout) {
        super(layout);
    }

    /**
     * Abstract method to collect data, to be implemented by subclasses.
     */
    protected abstract RadiationData collectData(View rootView);

    protected abstract void setUpCalculateButton(@NonNull View view, int buttonID);

    /**
     * Function to remove phenomenon of keyboard which cannot be discarded
     * @param view fragments' view
     */
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
        setUpHideKeyboard(view);
        setUpCalculateButton(view, buttonID);
    }
}

