package net.rodrigocarvalho.lucy.model.reactions;

import net.dv8tion.jda.api.entities.User;
import net.rodrigocarvalho.lucy.model.interfaces.Reaction;
import net.rodrigocarvalho.lucy.type.ReactionType;

public class AnonymousReaction implements Reaction {

    private User target;
    private String messageId;

    public AnonymousReaction(User target, String messageId) {
        this.target = target;
        this.messageId = messageId;
    }

    @Override
    public String getMessageId() {
        return messageId;
    }

    @Override
    public ReactionType getType() {
        return ReactionType.ANONYMOUS_MESSAGE;
    }

    public User getTarget() {
        return target;
    }
}
