package com.duckdeveloper.lucy.event;

import com.duckdeveloper.lucy.event.model.AbstractEvent;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventAdapter extends ListenerAdapter {

    private final Set<AbstractEvent<?>> listeners;

    private List<AbstractEvent> getEvents(GenericEvent event) {
        return listeners.stream().filter(x -> x.getType().getTypeName().equals(event.getClass().getName())).collect(Collectors.toList());
    }

    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        for (AbstractEvent abstractEvent : getEvents(event))
            abstractEvent.call(event);
    }
}