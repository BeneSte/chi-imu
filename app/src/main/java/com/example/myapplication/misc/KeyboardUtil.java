package com.example.myapplication.misc;

import android.content.Context;
import android.provider.Settings;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

public class KeyboardUtil {

    public static boolean isKeyboardActive(Context context){

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> inputMethodsInfo = imm.getEnabledInputMethodList();
        for(InputMethodInfo methodInfo : inputMethodsInfo) {
            if (methodInfo.getId().equals(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD))) {
                return true;
            }
        }
        return false;
    }
}
