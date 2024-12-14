package com.julian.lbniwkalkulator.components;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

public class TextChangedListener implements TextWatcher {

    private final String SUFFIX;

    public TextChangedListener(String suffix) {
        SUFFIX = suffix;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        return;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        return;
    }

    private boolean isUpdatingText = false;

    @Override
    public void afterTextChanged(Editable editable) {
        if (isUpdatingText || editable == null) {
            return;
        }

        try {
            isUpdatingText = true;
            String text = editable.toString().trim();
            if (!text.endsWith(SUFFIX)) {
                editable.replace(0, editable.length(),
                        text + SUFFIX,
                        0, text.length() + SUFFIX.length());
            }
        } finally {
            isUpdatingText = false;
        }
    }

}
