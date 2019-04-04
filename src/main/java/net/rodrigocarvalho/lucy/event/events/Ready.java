package net.rodrigocarvalho.lucy.event.events;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.rodrigocarvalho.lucy.Lucy;
import net.rodrigocarvalho.lucy.event.model.AbstractEvent;

public class Ready extends AbstractEvent<ReadyEvent> {

    @Override
    public void call(ReadyEvent readyEvent) {
        Lucy.print("Iniciei!");
    }
}
