package net.rodrigocarvalho.lucy.utils;

import lombok.var;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.rodrigocarvalho.lucy.Lucy;
import net.rodrigocarvalho.lucy.dao.UserDao;
import net.rodrigocarvalho.lucy.factory.UserData;
import net.rodrigocarvalho.lucy.type.CommandType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
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

    public static boolean asArguments(CommandType type, User user, MessageChannel channel, String[] args) {
        if (type.validArguments(args.length)) return true;

        channel.sendMessage(type.getHelp(user)).queue();
        return false;
    }

    public static User matchUser(Message message, String[] args, int size) {
        User user;
        var mentions = message.getMentionedUsers();
        if (mentions.size() > 0) {
            user = mentions.get(0);
        } else {
            String content = args[size];
            user = isId(content) ? Lucy.getJda().getUserById(content) : null;
        }
        return user;
    }

    public static boolean validateUser(MessageChannel channel, User user, User otherUser, boolean checkBot) {
        if (otherUser != null && !user.getId().equals(otherUser.getId()) && !(checkBot && otherUser.isBot()) && !otherUser.getId().equals(Lucy.getJda().getSelfUser().getId())) return true;
        channel.sendMessage(user.getAsMention() + ", certifique-se que você informou um usuário válido.").queue();
        return false;
    }

    public static boolean isId(String content) {
        return PATTERN_ID.matcher(content).matches();
    }

    public static String formatArguments(String[] args, int starts) {
        if (args.length < starts) return null;
        return String.join(" ", Arrays.copyOfRange(args, starts, args.length));
    }

    public static boolean validateMessage(String message, User user, MessageChannel channel) {
        if (message.length() >= 512) {
            channel.sendMessage(user.getAsMention() + ", Opa! Sua mensagem está muito grande.").queue();
            return false;
        }
        return true;
    }

    public static UserData getUserOrCreate(User user) {
        UserData data;
        if (UserDao.has(user)) {
            data = UserDao.get(user);
        } else {
            data = new UserData(user);
            UserDao.add(data);
        }
        return data;
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

    public static int getPendingFinalization() {
        return MEMORY_MX_BEAN.getObjectPendingFinalizationCount();
    }

    public static BufferedImage writeText(File file, String text, int weight, int height) {
        try {
            BufferedImage image = ImageIO.read(file);
            Graphics2D graphics = image.createGraphics();

            graphics.drawString(text, weight, height);
            ImageIO.write(image, "jpg", file);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}