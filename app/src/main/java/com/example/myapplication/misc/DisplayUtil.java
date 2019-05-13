package com.example.myapplication.misc;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.view.Display;

import com.example.myapplication.interfaces.IDisplayStateObserver;
import com.example.myapplication.misc.interfaces.IUpdateDisplayState;

import java.util.ArrayList;

public class DisplayUtil implements IUpdateDisplayState {

    private ArrayList<IDisplayStateObserver> observers;
    private static DisplayUtil INSTANCE = null;
    private Context context;
    private boolean isOn = false;

    public static DisplayUtil getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new DisplayUtil(context);
        }
        return INSTANCE;
    }

    private DisplayUtil(Context context){
        observers = new ArrayList<>();
        this.context = context;
        checkDisplayActivity();
    }

    public void checkDisplayActivity(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isDisplayActive();
                handler.postDelayed(this,5000);
            }
        },5000);
    }

    public void isDisplayActive() {

        DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        for (Display display : dm.getDisplays()) {
            if (display.getState() != Display.STATE_OFF) {
                isOn = true;
            }else{
                isOn = false;
            }
        }

        notifyObservers();
    }

    @Override
    public void registerObserver(IDisplayStateObserver stateObserver) {
        if (!observers.contains(stateObserver)){
            observers.add(stateObserver);
        }
    }

    @Override
    public void removeObserver(IDisplayStateObserver stateObserver) {
        if (observers.contains(stateObserver)){
            observers.remove(stateObserver);
        }
    }

    @Override
    public void notifyObservers() {
        for(IDisplayStateObserver observer: observers){
            observer.onDisplayStateChanged(isOn);
        }
    }
}
