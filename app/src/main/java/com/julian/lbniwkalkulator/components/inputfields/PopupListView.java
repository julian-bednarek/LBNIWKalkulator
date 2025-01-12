package com.julian.lbniwkalkulator.components.inputfields;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.julian.lbniwkalkulator.R;

public class PopupListView extends PopupWindow {

    private ListView listView;

    public PopupListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.components_popup_list_view, null);
        listView = contentView.findViewById(R.id.popup_list_view);
        setContentView(contentView);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    }

}
