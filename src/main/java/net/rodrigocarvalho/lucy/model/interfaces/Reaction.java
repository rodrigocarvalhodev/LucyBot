package net.rodrigocarvalho.lucy.model.interfaces;

import net.rodrigocarvalho.lucy.type.ReactionType;

public interface Reaction {

    String getMessageId();
    ReactionType getType();

}