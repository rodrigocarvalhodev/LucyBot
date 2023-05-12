package com.duckdeveloper.lucy.utils;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BotUtils {

    private static final List<String> ROOT_USERS = Arrays.asList("352901571543171074", "447420204453068801", "384197235849560064");
    private static long startTime;

    public static void setStartTime(long startTime) {
        BotUtils.startTime = startTime;
    }

    public static long getStartTime() {
        return startTime;
    }

    public static boolean isRootUser(User user) {
        return ROOT_USERS.contains(user.getId());
    }

    public static List<String> getMessagesStripped(String message) {
        int amount = 900;
        int messageLength = message.length();
        if (messageLength > amount) {
            int page = 0;
            int pageSize = messageLength / amount;
            var messages = new ArrayList<String>();

            do {
                int size = page == 1 ? 0 : amount * page;
                int finalSize = size + amount;

                var finalMessage = message.substring(size, Math.min(finalSize, messageLength));
                messages.add(finalMessage);
                page++;
            } while (page < pageSize);

            return messages;
        } else
            return Collections.singletonList(message);
    }

    public static String getTime() {
        var missing = System.currentTimeMillis() - BotUtils.getStartTime();
        return TimeUtils.formatTime(missing);
    }
}