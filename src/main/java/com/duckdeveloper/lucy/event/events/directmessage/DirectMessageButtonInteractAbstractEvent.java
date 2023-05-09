package com.duckdeveloper.lucy.event.events.directmessage;

import com.duckdeveloper.lucy.event.model.AbstractEvent;
import com.duckdeveloper.lucy.model.interactions.AnonymousInteraction;
import com.duckdeveloper.lucy.model.waiting.AnonymousWaiting;
import com.duckdeveloper.lucy.proxy.UserProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class DirectMessageButtonInteractAbstractEvent extends AbstractEvent<ButtonInteractionEvent> {

    private final UserProxy userProxy;

    @Override
    public void call(ButtonInteractionEvent event) {
        var user = event.getUser();
        var button = event.getButton();
        if (event.isFromGuild() ||
                user.isBot() ||
                !userProxy.has(user) ||
                !Objects.equals(button.getId(), "replyButton"))
            return;


        var messageId = event.getMessageId();

        var userData = userProxy.get(user);
        if (userData.hasInteraction(messageId)) {
            var anonymousInteraction = (AnonymousInteraction) userData.getInteraction(messageId);
            userData.removeInteraction(messageId);
            userData.addWaiting(new AnonymousWaiting(anonymousInteraction.getTarget(), anonymousInteraction.getMessageContent()));

            event.reply("Agora digite a mensagem que deseja enviar.").queue();
        }
    }
}