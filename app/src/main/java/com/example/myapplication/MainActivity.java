package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.entities.TouchEntity;
import com.example.myapplication.interfaces.IDisplayStateObserver;
import com.example.myapplication.misc.DisplayUtil;
import com.example.myapplication.misc.KeyboardUtil;
import com.example.myapplication.interfaces.IKeyboardStateObserver;
import com.example.myapplication.misc.interfaces.IUpdateDisplayState;
import com.example.myapplication.misc.interfaces.IUpdateKeyboardState;
import com.example.myapplication.tasks.Sensors;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements IKeyboardStateObserver, IDisplayStateObserver {

    private TextView xTextView;
    private TextView yTextView;
    private TextView actionTextView;
    private LinearLayout layout;
    private RelativeLayout layout2;
    private String actionText;
    private TextView keyboardText;
    private IUpdateKeyboardState keyboardUtil;
    private IUpdateDisplayState displayUtil;

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

        keyboardUtil = KeyboardUtil.getInstance(getApplicationContext());
        keyboardUtil.registerObserver(this);

        displayUtil = DisplayUtil.getInstance(getApplicationContext());
        displayUtil.registerObserver(this);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        Sensors s = new Sensors(this.getApplicationContext());

        Executor myExecutor = Executors.newSingleThreadExecutor();

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
                TouchEntity touchEntity = new TouchEntity(pointerCount,actionText,x,y,pressure);
                myExecutor.execute(() ->
                    db.touchDao().insertTouch(touchEntity)
                );

                return true;
            }
        });


    }

    @Override
    public void onKeyboardStateChanged(boolean state) {
        if (state) {
            keyboardText.setText("Active");
            Log.d("KEYBOARD_STATE", "ON");
        } else {
            keyboardText.setText("Not Active");
            Log.d("KEYBOARD_STATE", "OFF");
        }
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
