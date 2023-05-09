package com.duckdeveloper.lucy.utils;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.duckdeveloper.lucy.proxy.UserProxy;
import com.duckdeveloper.lucy.model.domain.UserData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class ObjectUtils {

    private static final Pattern PATTERN_ID = Pattern.compile("\\d+");
    private static final MemoryMXBean MEMORY_MX_BEAN = ManagementFactory.getMemoryMXBean();

    public static boolean isUserValid(SelfUser jdaUser, User user, User otherUser, boolean checkBot) {
        return otherUser != null &&
                (!user.equals(otherUser) && !jdaUser.getId().equals(otherUser.getId())) &&
                !(checkBot && otherUser.isBot());
    }

    public static boolean validateMessage(String message) {
        return message.length() <= 512 && !message.isBlank();
    }

    public static String formatInputStream(InputStream is) {
        if (is == null) return "";
        var bufferedReader = new BufferedReader(new InputStreamReader(is));
        var joiner = new StringJoiner("\n");
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                joiner.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return joiner.toString();
    }

    public static long getMemoryUsage() {
        long memoryUsage = MEMORY_MX_BEAN.getHeapMemoryUsage().getUsed();
        return memoryUsage / (1024 * 1024);
    }
}