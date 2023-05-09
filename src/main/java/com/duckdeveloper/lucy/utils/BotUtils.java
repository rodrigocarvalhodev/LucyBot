package com.duckdeveloper.lucy.utils;

import com.duckdeveloper.lucy.type.PunishmentType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BotUtils {

    private static final List<String> ROOT_USERS = Arrays.asList("352901571543171074", "447420204453068801");
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
        List<String> messages = new ArrayList<>();
        int amount = 900;
        if (message.length() > amount) {
            int i = 0;
            while (true) {
                i++;
                int size = i == 1 ? 0 : amount * (i-1);
                if (size >= message.length()) {
                    break;
                }
                int finalSize = size + amount;
                String msg = message.substring(size, Math.min(finalSize, message.length()));
                messages.add(msg);
            }
        } else messages.add(message);
        return messages;
    }

    public static boolean executePunishment(PunishmentType type, User user, Guild guild, String reason) {
        try {
            type.execute(user, guild, reason);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getTime() {
        var missing = System.currentTimeMillis() - BotUtils.getStartTime();
        return TimeUtils.formatTime(missing);
    }
}