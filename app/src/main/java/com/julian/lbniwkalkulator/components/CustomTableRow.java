package com.julian.lbniwkalkulator.components;

import static com.julian.lbniwkalkulator.enums.TableColumns.CURRENT_ACTIVITY;
import static com.julian.lbniwkalkulator.enums.TableColumns.ISOTOPE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.julian.lbniwkalkulator.R;

/**
 * Custom component for isotopes table that is also a checkbox
 */
@SuppressLint("ViewConstructor")
public class CustomTableRow extends LinearLayout implements Checkable {

    private static final int FONT_SIZE = 25;
    private static final int CELL_MARGINS = 5;
    private static final int ROW_PADDING = 3;
    private final TextView[] cells;
    /**
     * Mapping of ID from database <u>crucial for integration with database and differentiating other rows</u>
     */
    private final int entryID;
    private boolean checked;

    /**
     * The only constructor. <h3><u><b>This class cannot be initialized in xml layout!</b></u></h3>
     * @param context just context of application
     * @param ID for entry id
     * @param isotopeName should follow format ElementName-[Mass number]. It is not checked here, because wrong format is not dangerous
     * @param currentActivity activity -> isotope's activity (in Curies)
     */
    public CustomTableRow(@NonNull Context context, int ID, @NonNull String isotopeName,@NonNull String currentActivity) {
        super(context);
        this.entryID = ID;
        this.cells = new TextView[]
                {new TextView(context),
                 new TextView(context)};
        setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        setGravity(Gravity.CENTER);
        setBackgroundResource(R.drawable.table_row);
        setClipChildren(false);
        setFocusable(true);
        setClipToPadding(false);
        setUpRowContents();
        setPadding(ROW_PADDING, ROW_PADDING, ROW_PADDING, ROW_PADDING);
        cells[ISOTOPE.getIndex()].setText(isotopeName);
        cells[CURRENT_ACTIVITY.getIndex()].setText(currentActivity);
        this.checked = false;
        setOnClickListener(view -> {
            toggle();
        });
    }
    private void setUpRowContents() {
        for(TextView cell : cells) {
            this.addView(cell);
            cell.setBackgroundResource(R.drawable.table_cell);
            cell.setGravity(Gravity.CENTER);
            cell.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            cell.setTextColor(getResources().getColor(R.color.default_text_color));
            cell.setTextSize(FONT_SIZE);
            LayoutParams params =  new LinearLayout.LayoutParams(
                    0,
                    LayoutParams.WRAP_CONTENT,
                    1.0f);
            params.setMargins(CELL_MARGINS, CELL_MARGINS,CELL_MARGINS,CELL_MARGINS);
            cell.setLayoutParams(params);
        }
    }

    public int getEntryID() {
        return entryID;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        if(checked) {
            setBackgroundResource(R.drawable.table_row_active);
        } else {
            setBackgroundResource(R.drawable.table_row);
        }
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }
}
