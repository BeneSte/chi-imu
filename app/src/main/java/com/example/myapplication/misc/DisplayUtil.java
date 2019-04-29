package com.example.myapplication.misc;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;

public class DisplayUtil {

    public static boolean isDisplayActive(Context context) {
        DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        for (Display display : dm.getDisplays()) {
            if (display.getState() != Display.STATE_OFF) {
                return true;
            }
        }
        return false;
    }
}
