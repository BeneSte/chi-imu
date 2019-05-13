package com.example.myapplication.misc.interfaces;

import com.example.myapplication.interfaces.IKeyboardStateObserver;

/**
 * Used to notify all observers if observer should keep on recording
 */
public interface IUpdateKeyboardState {
    void registerObserver(IKeyboardStateObserver stateObserver);
    void removeObserver(IKeyboardStateObserver stateObserver);
    void notifyObservers();
}
