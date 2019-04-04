package net.rodrigocarvalho.lucy.type;

public enum DelayType {

    GLOBAL(5000L), ANONYMOUS_MESSAGE(20000L);

    private long time;

    private DelayType(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}