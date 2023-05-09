package com.duckdeveloper.lucy.type;

public enum DelayType {

    GLOBAL(5000L), ANONYMOUS_MESSAGE(10000L);

    private long time;

    DelayType(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}