package org.perceptualui.imustudy;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.perceptualui.imustudy.R;

import org.perceptualui.imustudy.entities.TouchEntity;
import org.perceptualui.imustudy.interfaces.IDisplayStateObserver;
import org.perceptualui.imustudy.misc.DisplayUtil;
import org.perceptualui.imustudy.misc.KeyboardReceiver;
import org.perceptualui.imustudy.misc.TouchReceiver;
import org.perceptualui.imustudy.misc.interfaces.IUpdateDisplayState;
import org.perceptualui.imustudy.tasks.Sensors;
import org.perceptualui.imustudy.tasks.UUID;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements IDisplayStateObserver {

    private TextView xTextView;
    private TextView yTextView;
    private TextView actionTextView;
    private LinearLayout layout;
    private RelativeLayout layout2;
    private String actionText;
    private TextView keyboardText;
    private TextView uuidText;
    private IUpdateDisplayState displayUtil;
    private Button syncButton;
    private TextView syncText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        layout = findViewById(R.id.parent_layout);
        layout2 = findViewById(R.id.super_layout);
        xTextView = findViewById(R.id.X_coord);
        yTextView = findViewById(R.id.Y_coord);
        keyboardText = findViewById(R.id.keyboardText);
        actionTextView = findViewById(R.id.action_text);
        uuidText = findViewById(R.id.uuidText);
        syncButton = findViewById(R.id.syncButton);
        syncText = findViewById(R.id.syncText);

        SyncJob syncJob = new SyncJob();

        uuidText.setText(UUID.getUUID(getApplicationContext()));



        displayUtil = DisplayUtil.getInstance(getApplicationContext());
        displayUtil.registerObserver(this);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        Sensors s = new Sensors(this.getApplicationContext());

        IntentFilter filter = new IntentFilter("org.perceptualui.imustudy.TOUCH_BROADCAST");
        this.registerReceiver(new TouchReceiver(), filter);

        Executor myExecutor = Executors.newSingleThreadExecutor();

        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncText.setText("Syncing.");
                myExecutor.execute(() ->
                        syncJob.doSync(getApplicationContext())
                );
            }
        });


        layout2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                xTextView.setText(""+x);
                yTextView.setText(""+y);
                int pointerCount = event.getPointerCount();
                float pressure = event.getPressure();
                int action = event.getAction();// & MotionEvent.ACTION_MASK;
                Log.d("ontouch", ""+x+"|"+y);
                switch (action){
                    case (MotionEvent.ACTION_DOWN):
                        actionTextView.setText("DOWN");
                        actionText = "DOWN";
                        break;
                    case (MotionEvent.ACTION_MOVE):
                        actionTextView.setText("MOVE");
                        actionText = "MOVE";
                        break;
                    case (MotionEvent.ACTION_UP):
                        actionTextView.setText("UP");
                        actionText = "UP";
                        break;
                    default:
                        break;
                }
                TouchEntity touchEntity = new TouchEntity(System.currentTimeMillis(), pointerCount,actionText,x,y,pressure);
                myExecutor.execute(() ->
                    db.touchDao().insertTouch(touchEntity)
                );

                return true;
            }
        });


    }


    @Override
    public void onDisplayStateChanged(boolean state) {
        if (state) {
            Log.d("DISPLAY_STATE", "ON");
        } else {
            Log.d("DISPLAY_STATE", "OFF");
        }
    }
}
