package com.julian.lbniwkalkulator.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.components.dialogs.CustomDeleteRecordsDialog;
import com.julian.lbniwkalkulator.components.dialogs.CustomInsertInputDialog;
import com.julian.lbniwkalkulator.components.table.CustomTableRow;
import com.julian.lbniwkalkulator.core.MainActivity;
import com.julian.lbniwkalkulator.databinding.ViewSavedIsotopesMenuLayoutBinding;
import com.julian.lbniwkalkulator.dataclasess.IsotopeActivity;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.localdata.SQLiteDatabaseWrapper.DeleteWhereClause;
import com.julian.lbniwkalkulator.util.ErrorHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SavedIsotopesMenuFragment extends Fragment {
    private ViewSavedIsotopesMenuLayoutBinding binding;

    public SavedIsotopesMenuFragment() {
        super(R.layout.view_saved_isotopes_menu_layout);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        binding = ViewSavedIsotopesMenuLayoutBinding.bind(view);
        setUpTable();
        setUpButtons();
    }

    private void setUpButtons() {
        binding.buttonAdd.setOnClickListener(v -> addIsotope());
        binding.buttonRemove.setOnClickListener(v -> removeClickedIsotopes());
        binding.savedIsotopesBackButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(SavedIsotopesMenuFragmentDirections.
                actionSavedIsotopesMenuFragmentToStartViewFragment()));
    }

    private void updateTableAfterDeletion(List<View> rowsToRemove) {
        for (View row : rowsToRemove) {
            binding.tableContents.removeView(row);
        }
        refreshTable();
    }

    private void deleteRecords(List<Integer> checkedIndexes, List<View> rowsToRemove) {
        CustomDeleteRecordsDialog dialog = new CustomDeleteRecordsDialog(requireContext());
        dialog.show();
        dialog.getYesButton().setOnClickListener(view -> {
            DeleteWhereClause deleteWhereClause = new DeleteWhereClause(checkedIndexes);
            ((MainActivity)requireActivity()).getDatabase()
                    .delete(deleteWhereClause.getWhereClause(), deleteWhereClause.getWhereClauseArgs());
            updateTableAfterDeletion(rowsToRemove);
            dialog.dismiss();
        });
        dialog.getNoButton().setOnClickListener(view -> dialog.dismiss());
    }

    private void removeClickedIsotopes() {
        List<Integer> checkedIndexes = new ArrayList<>();
        List<View> rowsToRemove = new ArrayList<>();
        for (int i = 0; i < binding.tableContents.getChildCount(); i++) {
            View row = binding.tableContents.getChildAt(i);
            if(row instanceof CustomTableRow && ((CustomTableRow) row).isChecked()) {
                checkedIndexes.add(((CustomTableRow) row).getEntryID());
                rowsToRemove.add(row);
            }
        }
        if(!rowsToRemove.isEmpty()) {
            deleteRecords(checkedIndexes, rowsToRemove);
        }
    }

    private void addIsotope() {
        IsotopeActivity[] toBeInserted = {null};
        CustomInsertInputDialog dialog = new CustomInsertInputDialog(requireContext());
        dialog.show();
        dialog.getAddButton().setOnClickListener(view -> {
            try {
                toBeInserted[0] =  dialog.collectData();
                dialog.dismiss();
            } catch (InputNotSupportedException e) {
                ErrorHandler.processException(requireContext(),
                        e.getMessage(),
                        R.string.exception_input_not_supported_additional,
                        e.getInvalidInput(), null);
                        dialog.clearInputs();
            }
        });
        dialog.setOnDismissListener(view ->{
            if(toBeInserted[0] != null) {
                int ID = ((MainActivity) requireActivity()).getDatabase().getUsableID();
                long currentTimestamp = System.currentTimeMillis() / 1_000;
                toBeInserted[0].setMostRecentTimestamp(currentTimestamp);
                toBeInserted[0].setID(ID);
                ((MainActivity) requireActivity()).getDatabase().insert(toBeInserted[0].toContentValues());
                refreshTable();
            }
        });

    }

    private void refreshTable() {
        clearRows();
        setUpTableRows();
    }

    private void setUpTable() {
        TableRow testRow = new TableRow(requireContext());
        testRow.setGravity(Gravity.CENTER);
        setUpTableRows();
    }

    private void clearRows() {
        binding.tableContents.removeAllViews();
    }

    private void setUpTableRows() {
        final int SIDE_MARGINS = 15;
        final int TOP_BOTTOM_MARGINS = 5;
        ArrayList<IsotopeActivity> savedIsotopes = ((MainActivity)requireActivity())
                                                    .getDatabase()
                                                    .loadAllRecords();
        for (IsotopeActivity isotope : savedIsotopes) {
            CustomTableRow row = new CustomTableRow(requireContext(),
                    isotope.getID(),
                    isotope.getIsotopeName(),
                    String.format(Locale.getDefault(),"%.2f", isotope.getActivity()));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMarginEnd(SIDE_MARGINS);
            params.setMarginStart(SIDE_MARGINS);
            params.setMargins(0, TOP_BOTTOM_MARGINS, 0, TOP_BOTTOM_MARGINS);
            row.setLayoutParams(params);
            binding.tableContents.addView(row);
        }
    }
}
