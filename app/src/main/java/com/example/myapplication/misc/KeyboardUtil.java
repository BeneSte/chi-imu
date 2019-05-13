package com.example.myapplication.misc;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import com.example.myapplication.interfaces.IKeyboardStateObserver;
import com.example.myapplication.misc.interfaces.IUpdateKeyboardState;

import java.util.ArrayList;
import java.util.List;

public class KeyboardUtil implements IUpdateKeyboardState {

    private boolean keyboardState = false;

    private Context context;

    private static KeyboardUtil INSTANCE = null;

    private ArrayList<IKeyboardStateObserver> observers;

    private KeyboardUtil(Context context){
        observers = new ArrayList<>();
        this.context = context;
        isKeyboardActive();
    }

    public static IUpdateKeyboardState getInstance(Context context){
        if (INSTANCE == null){
            return new KeyboardUtil(context);
        }
        return INSTANCE;
    }

    public void isKeyboardActive(){
          final Handler handler = new Handler();
          handler.postDelayed(new Runnable() {
              @Override
              public void run() {
                  getKeyboardState();
                  handler.postDelayed(this,1000);
              }
          },1000 );
    }

    /**
    *NOT WORKING - GREAT
    **/
    private void getKeyboardState(){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> inputMethodsInfo = imm.getEnabledInputMethodList();

        for(InputMethodInfo methodInfo : inputMethodsInfo) {
            if (methodInfo.getId().equals(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD))) {
                keyboardState = true;
            }else {
                keyboardState = false;
            }
        }
        notifyObservers();
    }

    @Override
    public void registerObserver(IKeyboardStateObserver stateObserver) {
        if(!observers.contains(stateObserver)){
            observers.add(stateObserver);
        }
    }

    @Override
    public void removeObserver(IKeyboardStateObserver stateObserver) {
        if(observers.contains(stateObserver)){
            observers.remove(stateObserver);
        }
    }

    @Override
    public void notifyObservers() {
        for (IKeyboardStateObserver stateObserver: observers) {
            stateObserver.onKeyboardStateChanged(keyboardState);
        }
    }
}
