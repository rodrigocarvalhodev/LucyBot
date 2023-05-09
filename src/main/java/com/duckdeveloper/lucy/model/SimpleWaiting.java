package com.duckdeveloper.lucy.model;

import com.duckdeveloper.lucy.model.interfaces.Waiting;
import com.duckdeveloper.lucy.type.WaitingType;

public class SimpleWaiting implements Waiting {

    private WaitingType type;

    public SimpleWaiting(WaitingType type) {
        this.type = type;
    }

    @Override
    public WaitingType getType() {
        return type;
    }
}