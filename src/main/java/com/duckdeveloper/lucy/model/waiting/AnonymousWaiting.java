package com.duckdeveloper.lucy.model.waiting;

import com.duckdeveloper.lucy.model.interfaces.Waiting;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import com.duckdeveloper.lucy.type.WaitingType;

@AllArgsConstructor
public class AnonymousWaiting implements Waiting {

    private User targetUser;
    private String messageContent;

    public User getTargetUser() {
        return targetUser;
    }

    public String getMessageContent() {
        return messageContent;
    }

    @Override
    public WaitingType getType() {
        return WaitingType.ANONYMOUS_MESSAGE;
    }
}
