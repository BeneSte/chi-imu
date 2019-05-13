package com.example.myapplication.misc.interfaces;

import com.example.myapplication.interfaces.IDisplayStateObserver;

public interface IUpdateDisplayState {
    void registerObserver(IDisplayStateObserver stateObserver);
    void removeObserver(IDisplayStateObserver stateObserver);
    void notifyObservers();
}
