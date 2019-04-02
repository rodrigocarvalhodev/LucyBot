package net.rodrigocarvalho.lucy.factory;

import net.rodrigocarvalho.lucy.type.DelayType;

public class Delay {

    private long time;
    private DelayType type;

    public Delay(DelayType type) {
        this.time = System.currentTimeMillis() + type.getTime();
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public DelayType getType() {
        return type;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= time;
    }
}