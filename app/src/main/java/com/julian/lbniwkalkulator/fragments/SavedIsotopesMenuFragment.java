package com.julian.lbniwkalkulator.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.components.CustomTableRow;
import com.julian.lbniwkalkulator.databinding.ViewSavedIsotopesMenuLayoutBinding;

public class SavedIsotopesMenuFragment extends Fragment {
    private ViewSavedIsotopesMenuLayoutBinding binding;

    public SavedIsotopesMenuFragment() {
        super(R.layout.view_saved_isotopes_menu_layout);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        binding = ViewSavedIsotopesMenuLayoutBinding.bind(view);
        TableRow testRow = new TableRow(requireContext());
        testRow.setGravity(Gravity.CENTER);
        for (int i = 1; i <= 25; i++) {
            CustomTableRow row = new CustomTableRow(requireContext(), i, "Sr-" + i, Double.toString(30 + i * 0.35));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMarginEnd(15);
            params.setMarginStart(15);
            params.setMargins(0, 5, 0, 5);
            row.setLayoutParams(params);
            binding.tableContents.addView(row);
        }
    }
}
