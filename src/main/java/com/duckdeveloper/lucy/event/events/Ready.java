package com.duckdeveloper.lucy.event.events;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import com.duckdeveloper.lucy.event.model.AbstractEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Ready extends AbstractEvent<ReadyEvent> {

    @Override
    public void call(ReadyEvent readyEvent) {
        log.info("Iniciei!");
    }
}
