package com.duckdeveloper.lucy.model.domain;

import com.duckdeveloper.lucy.model.interfaces.Interaction;
import com.duckdeveloper.lucy.model.interfaces.Waiting;
import com.duckdeveloper.lucy.type.DelayType;
import com.duckdeveloper.lucy.type.WaitingType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.dv8tion.jda.api.entities.User;

import java.util.Map;
import java.util.WeakHashMap;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserData {

    @EqualsAndHashCode.Include
    private User user;

    private final Map<String, Interaction> interactions = new WeakHashMap<>();
    private final Map<WaitingType, Waiting> waiting = new WeakHashMap<>();
    private final Map<DelayType, Delay> delays = new WeakHashMap<>();

    private UserData(User user) {
        this.user = user;
    }

    public static UserData fromUser(User user) {
        return new UserData(user);
    }

    public User getUser() {
        return user;
    }

    public void addInteraction(Interaction interaction) {
        interactions.put(interaction.getMessageId(), interaction);
    }

    public Interaction getInteraction(String messageId) {
        return interactions.get(messageId);
    }

    public void removeInteraction(String messageId) {
        interactions.remove(messageId);
    }

    public boolean hasInteraction(String messageId) {
        return interactions.containsKey(messageId);
    }

    public void addDelay(Delay delay) {
        delays.put(delay.getType(), delay);
    }

    public void addDelayFromDelayType(DelayType delayType) {
        delays.put(delayType, new Delay(delayType));
    }

    public void removeDelay(DelayType type) {
        delays.remove(type);
    }

    public Delay getDelay(DelayType type) {
        return delays.get(type);
    }

    public boolean hasDelay(DelayType type) {
        return delays.containsKey(type);
    }

    public void addWaiting(Waiting waiting) {
        this.waiting.put(waiting.getType(), waiting);
    }

    public void removeWaiting(WaitingType type) {
        waiting.remove(type);
    }

    public Waiting getWaiting(WaitingType type) {
        return waiting.get(type);
    }

    public boolean hasWaiting(WaitingType type) {
        return waiting.containsKey(type);
    }

}