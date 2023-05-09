package com.duckdeveloper.lucy.model.interactions;

import com.duckdeveloper.lucy.model.interfaces.Interaction;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import com.duckdeveloper.lucy.type.PunishmentType;
import com.duckdeveloper.lucy.type.InteractionType;

public class PunishmentInteraction implements Interaction {

    private PunishmentType punishmentType;
    private User user;
    private Guild guild;
    private String messageId;
    private User target;
    private String reason;

    public PunishmentInteraction(PunishmentType punishmentType, User user, Guild guild, String messageId, User target, String reason) {
        this.punishmentType = punishmentType;
        this.user = user;
        this.guild = guild;
        this.messageId = messageId;
        this.target = target;
        this.reason = reason;
    }

    public PunishmentType getPunishmentType() {
        return punishmentType;
    }

    public User getUser() {
        return user;
    }

    public Guild getGuild() {
        return guild;
    }

    @Override
    public String getMessageId() {
        return messageId;
    }

    public User getTarget() {
        return target;
    }

    @Override
    public InteractionType getType() {
        return InteractionType.PUNISHMENT;
    }

    public String getReason() {
        return reason;
    }
}