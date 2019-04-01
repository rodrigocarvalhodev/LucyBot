package net.rodrigocarvalho.lucy.event.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.rodrigocarvalho.lucy.event.model.AbstractEvent;

public class DirectMessagReactionAdd extends AbstractEvent<MessageReceivedEvent> {

    @Override
    public void call(MessageReceivedEvent event) {
        var member = event.getAuthor();
    }
}