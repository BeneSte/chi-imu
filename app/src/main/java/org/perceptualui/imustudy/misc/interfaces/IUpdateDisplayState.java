package org.perceptualui.imustudy.misc.interfaces;

import org.perceptualui.imustudy.interfaces.IDisplayStateObserver;

public interface IUpdateDisplayState {
    void registerObserver(IDisplayStateObserver stateObserver);
    void removeObserver(IDisplayStateObserver stateObserver);
    void notifyObservers();
}
