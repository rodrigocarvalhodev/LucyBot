package net.rodrigocarvalho.lucy.model.reactions;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.rodrigocarvalho.lucy.model.interfaces.Reaction;
import net.rodrigocarvalho.lucy.type.PunishmentType;
import net.rodrigocarvalho.lucy.type.ReactionType;

public class PunishmentReaction implements Reaction {

    private PunishmentType punishmentType;
    private User user;
    private Guild guild;
    private String messageId;
    private User target;
    private String reason;

    public PunishmentReaction(PunishmentType punishmentType, User user, Guild guild, String messageId, User target, String reason) {
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
    public ReactionType getType() {
        return ReactionType.PUNISHMENT;
    }

    public String getReason() {
        return reason;
    }
}