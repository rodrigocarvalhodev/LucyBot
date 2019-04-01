package net.rodrigocarvalho.lucy.task;

import net.dv8tion.jda.api.entities.Activity;
import net.rodrigocarvalho.lucy.Lucy;

import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

public class PresenceTask extends TimerTask {

    private int i;
    private static final List<String> PRESENCE_LIST = Arrays.asList(
            "Olá! Eu sou a Main.",
            "Você pode ver como fui feita em: https://github.com/RodrigoCarvalho31/LucyBot"
    );

    @Override
    public void run() {
        i++;
        if (i >= PRESENCE_LIST.size()) i = 0;
        String value = PRESENCE_LIST.get(i);
        Lucy.getJda().getPresence().setActivity(Activity.of(Activity.ActivityType.STREAMING, value));
    }
}
