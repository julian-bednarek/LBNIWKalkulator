package com.julian.lbniwkalkulator.fragments;

import static com.julian.lbniwkalkulator.util.ErrorHandler.showErrorDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.databinding.ViewStartLayoutBinding;
import com.julian.lbniwkalkulator.util.StringGetter;
import android.os.Bundle;
import android.widget.Button;

public class StartViewFragment extends Fragment {

    private ViewStartLayoutBinding startViewLayoutBinding;

    public StartViewFragment() {
        super(R.layout.view_start_layout);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                             @Nullable Bundle savedInstanceState) {
            startViewLayoutBinding = ViewStartLayoutBinding.bind(view);
            setUpButtons();
            setUpLanguageSelector(view);
        }

    private void setUpButtons() {
        startViewLayoutBinding.buttonSelectXray.setOnClickListener(view -> {
            startNewFragment(new XRayMenuViewFragment());
        });

        startViewLayoutBinding.buttonSelectIsotope.setOnClickListener(view -> {
            startNewFragment(new IsotopeMenuViewFragment());
        });
        startViewLayoutBinding.buttonSelectSavedIsotope.setOnClickListener(view -> {
            startNewFragment(new SavedIsotopesMenuFragment());
        });

    }

    private void startNewFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void setUpLanguageSelector(View rootView) {
        Button languageButton = rootView.findViewById(R.id.language_button);
        languageButton.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Settings.ACTION_APP_LOCALE_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                showErrorDialog(requireContext(),
                        StringGetter.fromStringsXML(R.string.exception_couldnot_start_language_selector_title),
                        StringGetter.fromStringsXML(R.string.exception_coundnot_start_language_selector_message),
                        null);
            }
        });
    }
}

