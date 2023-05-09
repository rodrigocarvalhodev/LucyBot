package com.duckdeveloper.lucy.type;

import net.dv8tion.jda.api.OnlineStatus;

import java.util.stream.Stream;

public enum OnlineType {

    ONLINE(OnlineStatus.ONLINE, "Online", EmoteType.ONLINE),
    OFFLINE(OnlineStatus.OFFLINE, "Offline", EmoteType.OFFLINE),
    ABSENT(OnlineStatus.IDLE, "Ausente", EmoteType.ABSENT),
    BUSY(OnlineStatus.DO_NOT_DISTURB, "Ocupado", EmoteType.BUSY);

    private OnlineStatus status;
    private String name;
    private EmoteType type;

    OnlineType(OnlineStatus status, String name, EmoteType type) {
        this.status = status;
        this.name = name;
        this.type = type;
    }

    public OnlineStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public EmoteType getType() {
        return type;
    }

    public static OnlineType getByOnlineStatus(OnlineStatus status) {
        return Stream.of(values()).filter(x -> x.getStatus() == status).findFirst().orElse(null);
    }
}