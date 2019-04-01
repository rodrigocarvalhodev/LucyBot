package net.rodrigocarvalho.lucy.event.model;

import com.google.common.reflect.TypeToken;
import net.dv8tion.jda.api.events.GenericEvent;

import java.lang.reflect.Type;

public abstract class AbstractEvent<T extends GenericEvent> {

    private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) { };
    private final Type type = typeToken.getType();

    public abstract void call(T t);

    public Type getType() {
        return type;
    }
}