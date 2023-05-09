package com.duckdeveloper.lucy.schedule;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class PresenceSchedule {

    private int i;
    private List<String> presenceList = Arrays.asList(
            "Olá! Eu sou a Lucy.",
            "Você pode ver como fui feita em: https://github.com/rodrigocarvalhodev/LucyBot",
            "Olhe o portfólio do meu criador em https://duckdeveloper.netlify.com/"
    );

    private final JDA jda;

    @Scheduled(fixedDelay = 10000L)
    public void run() {
        this.next();
        var value = presenceList.get(i);
        this.jda.getPresence().setActivity(Activity.of(Activity.ActivityType.STREAMING, value));
    }

    private void next() {
        i++;
        if (i >= presenceList.size())
            i = 0;
    }
}
