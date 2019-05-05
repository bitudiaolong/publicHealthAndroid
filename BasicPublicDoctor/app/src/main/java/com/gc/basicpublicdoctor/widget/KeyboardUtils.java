package com.gc.basicpublicdoctor.widget;

import android.view.View;
import android.widget.EditText;

import com.dilusense.customkeyboard.BaseKeyboard;

public class KeyboardUtils {
    public static void bindEditTextEvent(final BaseKeyboard customKeyboard, final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    customKeyboard.attachTo(editText);
                } else {
                    customKeyboard.hideKeyboard();
                }
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customKeyboard.isAttached) customKeyboard.showKeyboard();
                else customKeyboard.attachTo(editText);
            }
        });
    }
}
