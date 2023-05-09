package com.duckdeveloper.lucy.utils;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import com.duckdeveloper.lucy.event.model.AbstractEvent;
import org.reflections.Reflections;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReflectionUtils {

    private static final Reflections REFLECTIONS = new Reflections("net.rodrigocarvalho.lucy.command");
    private static final Reflections REFLECTIONS_EVENTS = new Reflections("net.rodrigocarvalho.lucy.event.events");

    public static List<AbstractCommand> getAllCommandss() {
        var typesAnnotated = REFLECTIONS.getTypesAnnotatedWith(CommandHandler.class);
        return typesAnnotated.stream().map(ReflectionUtils::applyCommand).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<AbstractEvent> getAllEvents() {
        var types = REFLECTIONS_EVENTS.getSubTypesOf(AbstractEvent.class);
        return types.stream().map(ReflectionUtils::applyEvent).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static AbstractCommand applyCommand(Class<?> x) {
        try {
            return (AbstractCommand) x.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static AbstractEvent applyEvent(Class<?> x) {
        try {
            return (AbstractEvent) x.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}