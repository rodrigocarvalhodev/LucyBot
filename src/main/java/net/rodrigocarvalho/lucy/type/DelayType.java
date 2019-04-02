package net.rodrigocarvalho.lucy.type;

public enum DelayType {

    ANONYMOUS_MESSAGE(20000L);

    private long time;

    private DelayType(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}