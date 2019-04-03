package net.rodrigocarvalho.lucy.utils;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.rodrigocarvalho.lucy.type.EmoteType;

import java.util.function.Consumer;

public class BotUtils {

    public static void sendPrivateMessage(User user, MessageChannel channel, Message message, Consumer<Message> callback) {
        user.openPrivateChannel()
                .queue(s -> s.sendMessage(message)
                        .queue(callback,
                                e -> channel.sendMessage("Não consegui enviar mensagem no privado :(").queue()));
    }

    public static void addReaction(Message message, EmoteType... types) {
        for (EmoteType type : types) {
            message.addReaction(type.getEmote()).queue();
        }
    }
}