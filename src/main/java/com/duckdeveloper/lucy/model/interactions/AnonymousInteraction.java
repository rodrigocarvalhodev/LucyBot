package com.duckdeveloper.lucy.model.interactions;

import com.duckdeveloper.lucy.model.interfaces.Interaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.entities.User;
import com.duckdeveloper.lucy.type.InteractionType;

@AllArgsConstructor
@Getter
public class AnonymousInteraction implements Interaction {

    private User target;

    private String messageContent;

    private String messageId;

    @Override
    public InteractionType getType() {
        return InteractionType.ANONYMOUS_MESSAGE;
    }

    public User getTarget() {
        return target;
    }
}
