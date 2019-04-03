package net.rodrigocarvalho.lucy.utils;

import lombok.var;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.rodrigocarvalho.lucy.factory.Delay;
import net.rodrigocarvalho.lucy.factory.UserData;
import net.rodrigocarvalho.lucy.model.reactions.AnonymousReaction;
import net.rodrigocarvalho.lucy.type.DelayType;
import net.rodrigocarvalho.lucy.type.EmoteType;

import java.util.concurrent.TimeUnit;

public class SystemUtils {

    public static void sendDirectMessageAnonymous(User user, User targetUser, String anonymousMessage, MessageChannel channel, Message userMessage, boolean delay) {
        BotUtils.sendPrivateMessage(targetUser, channel,
                new MessageBuilder()
                        .setEmbed(
                                new EmbedBuilder()
                                        .setTitle("Mensagem anônima")
                                        .appendDescription("Você recebeu uma mensagem anônima!")
                                        .addField(EmoteType.ENVELOPE.getReaction() + " Mensagem", anonymousMessage, false)
                                        .addField(EmoteType.INFORMATION.getReaction() + " Observações", "Você pode responder a mensagem clicando no emoji!", false)
                                        .setFooter(targetUser.getName(), targetUser.getAvatarUrl())
                                        .build())
                        .build(),
                newMessage -> {
                    if (userMessage.getChannelType() == ChannelType.TEXT) {
                        userMessage.delete().queue();
                    }
                    BotUtils.addReaction(newMessage, EmoteType.ENVELOPE);
                    UserData data = ObjectUtils.getUserOrCreate(user);
                    UserData targetData = ObjectUtils.getUserOrCreate(targetUser);
                    if (delay) {
                        data.addDelay(new Delay(DelayType.ANONYMOUS_MESSAGE));
                    }
                    targetData.addReaction(new AnonymousReaction(user, newMessage.getId()));
                    channel.sendMessage(user.getAsMention() + ", Mensagem enviada com sucesso!").queue(message -> {
                        message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
                    });
                }
        );
    }
}