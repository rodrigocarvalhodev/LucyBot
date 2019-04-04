package net.rodrigocarvalho.lucy.event.events.punishment;

import lombok.var;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.rodrigocarvalho.lucy.dao.UserDao;
import net.rodrigocarvalho.lucy.event.model.AbstractEvent;
import net.rodrigocarvalho.lucy.model.reactions.PunishmentReaction;
import net.rodrigocarvalho.lucy.type.EmoteType;
import net.rodrigocarvalho.lucy.type.ReactionType;
import net.rodrigocarvalho.lucy.utils.BotUtils;

public class PunishmentReactionAdd extends AbstractEvent<GuildMessageReactionAddEvent> {

    @Override
    public void call(GuildMessageReactionAddEvent event) {
        var user = event.getUser();
        var message = event.getMessageId();
        var userData = UserDao.get(user);
        var channel = event.getChannel();
        var emote = event.getReactionEmote();
        if (userData != null && userData.hasReaction(ReactionType.PUNISHMENT)) {
            var reaction = (PunishmentReaction) userData.getReaction(ReactionType.PUNISHMENT);
            if (EmoteType.CHECK_MARK.is(emote) && reaction.getMessageId().equals(message)) {
                userData.removeReaction(ReactionType.PUNISHMENT);
                try {
                    var type = reaction.getPunishmentType();
                    BotUtils.executePunishment(type, reaction.getTarget(),
                            reaction.getGuild(), reaction.getReason());
                    String messageToReceiver = type.getMessage();
                    channel.sendMessage(user.getAsMention() + ", " + messageToReceiver).queue();
                } catch (Exception e) {
                    channel.sendMessage(user.getAsMention() + ", Parece que algum de nós ficou sem permissão.").queue();
                }
            }
        }
    }
}