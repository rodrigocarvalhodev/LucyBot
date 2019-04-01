package net.rodrigocarvalho.lucy.utils;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.rodrigocarvalho.lucy.type.EmoteType;

import java.util.concurrent.atomic.AtomicReference;

public class BotUtils {

    public static Message sendPrivateMessage(User user, MessageChannel channel, Message message) {
        AtomicReference<Message> result = new AtomicReference<>(null);
        user.openPrivateChannel()
                .queue((s) -> {
                    result.set(s.sendMessage(message).complete());
                }, (e) -> channel.sendMessage("Não consegui enviar mensagem no privado :(").queue());
        return result.get();
    }

    public static void addReaction(Message message, EmoteType... types) {
        for (EmoteType type : types) {
            message.addReaction(type.getEmote()).queue();
        }
    }
}