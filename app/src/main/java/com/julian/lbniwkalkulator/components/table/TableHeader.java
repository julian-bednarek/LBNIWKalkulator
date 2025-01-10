package com.julian.lbniwkalkulator.components.table;

import static com.julian.lbniwkalkulator.enums.TableColumns.*;
import com.julian.lbniwkalkulator.R;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A custom component that represents the header of a table.
 * <p>
 * Style of this class is predefined, therefore to change style change of code is needed
 * </p>
 */
public class TableHeader extends LinearLayout {
    public final static int FONT_SIZE = 27;
    private final TextView[] headerContents;
    public TableHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        setBackgroundResource(R.drawable.table_header);
        this.headerContents = new TextView[]
                {new TextView(context, attributeSet),
                 new TextView(context, attributeSet)};
        setUpHeaderContents();
    }
    public void setUpHeaderContents() {
        headerContents[ISOTOPE.getIndex()].setText(R.string.button_select_isotope);
        headerContents[CURRENT_ACTIVITY.getIndex()].setText(R.string.current_activity);
        for (TextView headerContent:headerContents) {
            this.addView(headerContent);
            headerContent.setGravity(Gravity.CENTER);
            headerContent.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            headerContent.setTypeface(null, Typeface.BOLD);
            headerContent.setTextColor(getResources().getColor(R.color.default_text_color));
            headerContent.setTextSize(FONT_SIZE);
            headerContent.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT,
                    1.0f
            ));
        }
    }
}
