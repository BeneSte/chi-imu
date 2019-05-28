package org.perceptualui.imustudy.tasks;

import android.content.Context;
import android.provider.Settings;

public class UUID {

    public static String getUUID(Context context){
        String android_id = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
        return android_id;
    }

}
