package net.rodrigocarvalho.lucy.factory;

import net.dv8tion.jda.api.entities.User;
import net.rodrigocarvalho.lucy.model.interfaces.Reaction;
import net.rodrigocarvalho.lucy.model.interfaces.Waiting;
import net.rodrigocarvalho.lucy.type.DelayType;
import net.rodrigocarvalho.lucy.type.ReactionType;
import net.rodrigocarvalho.lucy.type.WaitingType;

import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

public class UserData {

    private User user;
    private final Map<ReactionType, Reaction> REACTIONS = new WeakHashMap<>();
    private final Map<WaitingType, Waiting> WAITING = new WeakHashMap<>();
    private final Map<DelayType, Delay> DELAYS = new WeakHashMap<>();

    public UserData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void addReaction(Reaction reaction) {
        REACTIONS.put(reaction.getType(), reaction);
    }

    public Reaction getReaction(ReactionType type) {
        return REACTIONS.get(type);
    }

    public void removeReaction(ReactionType type) {
        REACTIONS.remove(type);
    }

    public boolean hasReaction(ReactionType type) {
        return REACTIONS.containsKey(type);
    }

    public void addDelay(Delay delay) {
        DELAYS.put(delay.getType(), delay);
    }

    public void removeDelay(DelayType type) {
        DELAYS.remove(type);
    }

    public Delay getDelay(DelayType type) {
        return DELAYS.get(type);
    }

    public boolean hasDelay(DelayType type) {
        return DELAYS.containsKey(type);
    }

    public void addWaiting(Waiting waiting) {
        WAITING.put(waiting.getType(), waiting);
    }

    public void removeWaiting(WaitingType type) {
        WAITING.remove(type);
    }

    public Waiting getWaiting(WaitingType type) {
        return WAITING.get(type);
    }

    public boolean hasWaiting(WaitingType type) {
        return WAITING.containsKey(type);
    }

    public Map<ReactionType, Reaction> getReactions() {
        return REACTIONS;
    }

    public Map<DelayType, Delay> getDelays() {
        return DELAYS;
    }

    public Map<WaitingType, Waiting> getWaiting() {
        return WAITING;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return user.equals(userData.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}