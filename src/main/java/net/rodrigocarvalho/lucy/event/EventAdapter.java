package net.rodrigocarvalho.lucy.event;

import lombok.var;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.rodrigocarvalho.lucy.Lucy;
import net.rodrigocarvalho.lucy.event.model.AbstractEvent;
import net.rodrigocarvalho.lucy.utils.ReflectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventAdapter extends ListenerAdapter {

    private final Set<AbstractEvent> LISTENERS = new HashSet<>();

    public EventAdapter() {
        for (var event : ReflectionUtils.getAllEvents()) {
            LISTENERS.add(event);
            Lucy.print("Registred event \"" + event.getClass().getSimpleName() + "\" of type \"" + event.getType().getTypeName() + "\"");
        }
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