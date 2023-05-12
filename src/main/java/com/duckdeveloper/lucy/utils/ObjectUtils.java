package com.duckdeveloper.lucy.utils;

import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class ObjectUtils {

    private static final MemoryMXBean MEMORY_MX_BEAN = ManagementFactory.getMemoryMXBean();

    public static boolean isUserValid(SelfUser jdaUser, User user, User otherUser, boolean checkBot) {
        return otherUser != null &&
                (!user.equals(otherUser) && !jdaUser.getId().equals(otherUser.getId())) &&
                !(checkBot && otherUser.isBot());
    }

    public static boolean validateMessage(String message) {
        return message.length() <= 512 && !message.isBlank();
    }

    public static long getMemoryUsage() {
        long memoryUsage = MEMORY_MX_BEAN.getHeapMemoryUsage().getUsed();
        return memoryUsage / (1024 * 1024);
    }
}