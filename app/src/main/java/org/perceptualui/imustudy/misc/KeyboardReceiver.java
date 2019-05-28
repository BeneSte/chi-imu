package org.perceptualui.imustudy.misc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.perceptualui.imustudy.AppDatabase;
import org.perceptualui.imustudy.entities.KeyboardEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class KeyboardReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        AppDatabase db = AppDatabase.getInstance(context);

        long time = intent.getExtras().getLong("time");
        boolean state = intent.getExtras().getBoolean("state");

        KeyboardEntity keyboardEntity = new KeyboardEntity(time, state);

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() ->
                db.keyboardDao().insertKeyboard(keyboardEntity)
        );
    }
}
