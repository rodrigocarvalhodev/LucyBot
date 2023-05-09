package com.duckdeveloper.lucy.model.domain;

import com.duckdeveloper.lucy.type.DelayType;
import lombok.Getter;

@Getter
public class Delay {

    private long time;
    private DelayType type;

    public Delay(DelayType type) {
        this.time = System.currentTimeMillis() + type.getTime();
        this.type = type;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= time;
    }
}