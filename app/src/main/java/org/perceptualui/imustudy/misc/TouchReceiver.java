package org.perceptualui.imustudy.misc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.perceptualui.imustudy.AppDatabase;
import org.perceptualui.imustudy.entities.TouchEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TouchReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AppDatabase db = AppDatabase.getInstance(context);
        long time = intent.getExtras().getLong("time");
        int x = intent.getExtras().getInt("x");
        int y = intent.getExtras().getInt("y");
        String type = intent.getExtras().getString("type");
        float pressure = intent.getExtras().getFloat("pressure");
        int id = intent.getExtras().getInt("id");
        Executor myExecutor = Executors.newSingleThreadExecutor();
        Log.d("TouchReceiver", "Time: "+time+ " | ID: "+ id + " | Type: "+ type+ " | (X,Y): (" +x+ "," + y +") | Pressure: "+ pressure);
        TouchEntity touchEntity = new TouchEntity(time, id, type, x, y, pressure);
        myExecutor.execute(() ->
                db.touchDao().insertTouch(touchEntity)
        );
    }
}
