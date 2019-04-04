package net.rodrigocarvalho.lucy.model.waiting;

import net.dv8tion.jda.api.entities.User;
import net.rodrigocarvalho.lucy.model.interfaces.Waiting;
import net.rodrigocarvalho.lucy.type.WaitingType;

public class AnonymousWaiting implements Waiting {

    private User targetUser;

    public AnonymousWaiting(User targetUser) {
        this.targetUser = targetUser;
    }

    public User getTargetUser() {
        return targetUser;
    }

    @Override
    public WaitingType getType() {
        return WaitingType.ANONYMOUS_MESSAGE;
    }
}
