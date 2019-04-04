package net.rodrigocarvalho.lucy.event.events.directmessage;

import lombok.var;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.rodrigocarvalho.lucy.dao.UserDao;
import net.rodrigocarvalho.lucy.event.model.AbstractEvent;
import net.rodrigocarvalho.lucy.model.reactions.AnonymousReaction;
import net.rodrigocarvalho.lucy.model.waiting.AnonymousWaiting;
import net.rodrigocarvalho.lucy.type.EmoteType;
import net.rodrigocarvalho.lucy.type.ReactionType;

public class DirectMessageReactionAdd extends AbstractEvent<PrivateMessageReactionAddEvent> {

    @Override
    public void call(PrivateMessageReactionAddEvent event) {
        var user = event.getUser();
        var messageId = event.getMessageId();
        var emote = event.getReactionEmote();
        var channel = event.getChannel();
        if (!user.isBot() && UserDao.has(user)) {
            var userData = UserDao.get(user);
            if (userData.hasReaction(ReactionType.ANONYMOUS_MESSAGE)) {
                var reaction = userData.getReaction(ReactionType.ANONYMOUS_MESSAGE);
                if (reaction.getMessageId().equals(messageId) && EmoteType.ENVELOPE.is(emote)) {
                    var anonymousReaction = (AnonymousReaction) reaction;
                    userData.removeReaction(ReactionType.ANONYMOUS_MESSAGE);
                    userData.addWaiting(new AnonymousWaiting(anonymousReaction.getTarget()));
                    channel.sendMessage("Agora digite a mensagem que deseja enviar.").queue();
                }
            }
        }
    }
}