package com.julian.lbniwkalkulator.components.inputfields;

import static com.julian.lbniwkalkulator.enums.VoltageValuesEnum.RANGE_ERESCO;
import static com.julian.lbniwkalkulator.enums.VoltageValuesEnum.RANGE_Y_SMART;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.enums.InputEnumTypes;
import com.julian.lbniwkalkulator.exceptions.InvalidComponentParameterException;
import com.julian.lbniwkalkulator.exceptions.MissingComponentParameterException;
import com.julian.lbniwkalkulator.util.StringGetter;

import java.util.Arrays;
import java.util.List;

/**
 * A custom PopupWindow that displays a list of items in a dropdown style.
 */
public class PopupListView extends PopupWindow {

    private final Context context;
    private ListView listView;
    private OnItemSelectedListener onItemSelectedListener;
    /**
     * This parameter is only used in case of voltage enum
     */
    private int range;

    public PopupListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        this.range = RANGE_Y_SMART;
    }

    /**
     * Initializes the PopupListView with its layout and behavior.
     */
    private void init() {
        @SuppressLint("InflateParams") View contentView = LayoutInflater.from(context).inflate(R.layout.components_popup_list_view, null);
        listView = contentView.findViewById(R.id.popup_list_view);
        setContentView(contentView);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);
        setOutsideTouchable(true);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    }

    public void setRange(int range) {
        this.range = range;
    }

    /**
     * Sets the items to be displayed in the PopupListView.
     *
     * @param items A list of string items to display in the list.
     */
    public void setItems(List<String> items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
    }

    /**
     * Sets the listener for item selection in the list.
     *
     * @param listener The listener to handle item selection events.
     */
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelected(parent, view, position, id);
            }
            dismiss();
        });
    }

    public void setOrUpdateContents(String enumType) throws InvalidComponentParameterException, MissingComponentParameterException {
        setItems(contentsFromEnum(enumType));
    }

    public List<String> contentsFromEnum(String enumType) throws InvalidComponentParameterException, MissingComponentParameterException {
        if (enumType == null) throw new MissingComponentParameterException(
                StringGetter.fromStringsXML(R.string.exception_missing_component_parameter_exception_message),
                "enum_type");
        boolean validEnum = Arrays.stream(InputEnumTypes.values()).anyMatch(e -> e.name().equals(enumType.toUpperCase()));
        if (!validEnum) throw new InvalidComponentParameterException(
                StringGetter.fromStringsXML(R.string.exception_invalid_component_parameter_exception_message),
                enumType);
        InputEnumTypes eType = InputEnumTypes.valueOf(enumType.toUpperCase());
        if(eType == InputEnumTypes.VOLTAGE_VALUE) {
            return eType.getContentsFromEnumWithRange(range);
        } else {
            return eType.getContentsFromEnum();
        }
    }

    /**
     * Displays the PopupListView at the center of the screen
     */
    public void show() {
        showAtLocation(((Activity) context).findViewById(android.R.id.content),
                Gravity.CENTER, 0, 0);
    }
}
