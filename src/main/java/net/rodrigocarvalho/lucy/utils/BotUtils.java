package net.rodrigocarvalho.lucy.utils;

import lombok.var;
import net.dv8tion.jda.api.entities.*;
import net.rodrigocarvalho.lucy.dao.UserDao;
import net.rodrigocarvalho.lucy.type.DelayType;
import net.rodrigocarvalho.lucy.type.EmoteType;
import net.rodrigocarvalho.lucy.type.PunishmentType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class BotUtils {

    private static final List<String> ROOT_USERS = Arrays.asList("352901571543171074", "136271663640739840");

    public static boolean isRootUser(User user) {
        return ROOT_USERS.contains(user.getId());
    }

    public static void sendPrivateMessage(User user, MessageChannel channel, Message message, Consumer<Message> callback) {
        user.openPrivateChannel()
                .queue(s -> s.sendMessage(message)
                        .queue(callback,
                                e -> channel.sendMessage("Não consegui enviar mensagem no privado <:pinguim:563134096713318403>").queue()));
    }

    public static void addReaction(Message message, EmoteType... types) {
        for (var type : types) {
            message.addReaction(type.getEmote()).queue();
        }
    }

    public static boolean hasDelay(User user, DelayType type) {
        var userData = UserDao.get(user);
        if (userData != null && userData.hasDelay(type)) {
            var delay = userData.getDelay(type);
            if (delay.isExpired()) {
                userData.removeDelay(type);
                UserDao.clearUser(userData);
                return false;
            }
            return true;
        }
        return false;
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
                String msg = message.substring(size, finalSize > message.length() ? message.length() : finalSize);
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
}