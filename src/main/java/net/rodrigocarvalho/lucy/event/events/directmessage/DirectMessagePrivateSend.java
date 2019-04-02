package net.rodrigocarvalho.lucy.event.events.directmessage;

import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.rodrigocarvalho.lucy.dao.UserDao;
import net.rodrigocarvalho.lucy.event.model.AbstractEvent;
import net.rodrigocarvalho.lucy.model.reactions.AnonymousWaiting;
import net.rodrigocarvalho.lucy.type.WaitingType;
import net.rodrigocarvalho.lucy.utils.SystemUtils;

public class DirectMessagePrivateSend extends AbstractEvent<PrivateMessageReceivedEvent> {
    
    @Override
    public void call(PrivateMessageReceivedEvent event) {
        var user = event.getAuthor();
        var channel = event.getChannel();
        var messageContent = event.getMessage().getContentRaw();
        if (UserDao.has(user)) {
            var userData = UserDao.get(user);
            if (userData.hasWaiting(WaitingType.ANONYMOUS_MESSAGE)) {
                var waiting = (AnonymousWaiting) userData.getWaiting(WaitingType.ANONYMOUS_MESSAGE);
                var targetUser = waiting.getTargetUser();
                SystemUtils.sendDirectMessageAnonymous(user, targetUser, messageContent, channel);
            }
        }
    }
}