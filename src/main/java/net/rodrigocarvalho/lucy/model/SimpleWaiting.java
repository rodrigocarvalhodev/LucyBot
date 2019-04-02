package net.rodrigocarvalho.lucy.model;

import net.rodrigocarvalho.lucy.model.interfaces.Waiting;
import net.rodrigocarvalho.lucy.type.WaitingType;

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