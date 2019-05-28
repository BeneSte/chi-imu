package org.perceptualui.imustudy.misc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null ) {
            try {
                NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }catch (Exception e){
                if (Const.DEBUG) e.printStackTrace();
            }
        }
        return false;
    }
}
