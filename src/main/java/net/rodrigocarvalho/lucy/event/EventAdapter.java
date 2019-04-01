package net.rodrigocarvalho.lucy.event;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.rodrigocarvalho.lucy.event.model.AbstractEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventAdapter extends ListenerAdapter {

    private final Set<AbstractEvent> LISTENERS = new HashSet<>();

    public EventAdapter() {

    }

    private List<AbstractEvent> getEvents(GenericEvent event) {
        return LISTENERS.stream().filter(x -> x.getType().getTypeName().equals(event.getClass().getName())).collect(Collectors.toList());
    }

    @Override
    public void onGenericEvent(GenericEvent event) {
        for (AbstractEvent abstractEvent : getEvents(event))
            abstractEvent.call(event);
    }
}