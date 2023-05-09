package com.duckdeveloper.lucy.service;

import com.duckdeveloper.lucy.model.interactions.AnonymousInteraction;
import com.duckdeveloper.lucy.proxy.UserProxy;
import com.duckdeveloper.lucy.type.DelayType;
import com.duckdeveloper.lucy.type.EmoteType;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnonymousDirectMessageService {

    private final UserProxy userProxy;

    public void sendAnonymousMessage(User user, User targetUser, String message) {
        var userData = userProxy.getUserDataOrCreate(user);
        userData.addDelayFromDelayType(DelayType.ANONYMOUS_MESSAGE);

        targetUser.openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessageEmbeds(
                        new EmbedBuilder()
                        .setTitle("Mensagem anônima")
                        .appendDescription("Você recebeu uma mensagem anônima!")
                        .addField(EmoteType.ENVELOPE.getReaction() + " Mensagem", message, false)
                        .addField(EmoteType.INFORMATION.getReaction() + " Observações", "Você pode responder a mensagem clicando no botão para responder!", false)
                        .setFooter(targetUser.getName(), targetUser.getAvatarUrl())
                        .build()
                )
                .addActionRow(Button.of(ButtonStyle.SUCCESS, "replyButton", "Responder"))
                .queue(receivedMessage -> sendAnonymousMessageToReceiver(targetUser, user, message, receivedMessage.getId())));
    }

    public void sendResponseAnonymousMessage(User user, User targetUser, String lastMessage, String message) {
        var userData = userProxy.getUserDataOrCreate(user);
        userData.addDelayFromDelayType(DelayType.ANONYMOUS_MESSAGE);

        targetUser.openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessageEmbeds(
                                new EmbedBuilder()
                                        .setTitle("Mensagem anônima")
                                        .appendDescription("Você recebeu resposta de sua mensagem anônima!")
                                        .addField(EmoteType.ENVELOPE.getReaction() + " Sua mensagem", lastMessage, false)
                                        .addField(EmoteType.ENVELOPE.getReaction() + " Mensagem recebida", message, false)
                                        .addField(EmoteType.INFORMATION.getReaction() + " Observações", "Você pode responder a mensagem clicando no botão para responder!", false)
                                        .setFooter(targetUser.getName(), targetUser.getAvatarUrl())
                                        .build()
                        )
                        .addActionRow(Button.of(ButtonStyle.SUCCESS, "replyButton", "Responder"))
                        .queue(receivedMessage -> sendAnonymousMessageToReceiver(targetUser, user, message, receivedMessage.getId())));
    }

    private void sendAnonymousMessageToReceiver(User user, User targetUser, String messageContent, String messageId) {
        var targetUserData = userProxy.getUserDataOrCreate(user);

        targetUserData.addInteraction(new AnonymousInteraction(targetUser, messageContent, messageId));
        this.userProxy.add(targetUserData);
    }
}