package com.duckdeveloper.lucy.event.events.directmessage;

import com.duckdeveloper.lucy.event.model.AbstractEvent;
import com.duckdeveloper.lucy.model.waiting.AnonymousWaiting;
import com.duckdeveloper.lucy.proxy.UserProxy;
import com.duckdeveloper.lucy.service.AnonymousDirectMessageService;
import com.duckdeveloper.lucy.type.WaitingType;
import com.duckdeveloper.lucy.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirectMessageMessageSendAbstractEvent extends AbstractEvent<MessageReceivedEvent> {

    private final UserProxy userProxy;
    private final AnonymousDirectMessageService service;

    @Override
    public void call(MessageReceivedEvent event) {
        var user = event.getAuthor();
        if (event.isFromGuild() || user.isBot() || !userProxy.has(user))
            return;

        var channel = event.getChannel();
        var message = event.getMessage();
        var messageContent = message.getContentRaw();

        var userData = userProxy.get(user);
        if (userData.hasWaiting(WaitingType.ANONYMOUS_MESSAGE)) {

            if (!ObjectUtils.validateMessage(message.getContentDisplay())) {
                channel.sendMessage("Opa! digite uma mensagem válida.").queue();
                return;
            }

            var waiting = (AnonymousWaiting) userData.getWaiting(WaitingType.ANONYMOUS_MESSAGE);
            userData.removeWaiting(WaitingType.ANONYMOUS_MESSAGE);
            userProxy.clearUser(userData);

            service.sendResponseAnonymousMessage(user, waiting.getTargetUser(), waiting.getMessageContent(), messageContent);
            channel.sendMessage("Resposta enviada com sucesso!").queue();
        }
    }
}