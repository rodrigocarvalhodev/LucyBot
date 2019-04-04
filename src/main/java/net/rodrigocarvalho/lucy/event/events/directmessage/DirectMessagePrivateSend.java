package net.rodrigocarvalho.lucy.event.events.directmessage;

import lombok.var;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.rodrigocarvalho.lucy.dao.UserDao;
import net.rodrigocarvalho.lucy.event.model.AbstractEvent;
import net.rodrigocarvalho.lucy.model.waiting.AnonymousWaiting;
import net.rodrigocarvalho.lucy.type.WaitingType;
import net.rodrigocarvalho.lucy.utils.SystemUtils;

public class DirectMessagePrivateSend extends AbstractEvent<PrivateMessageReceivedEvent> {
    
    @Override
    public void call(PrivateMessageReceivedEvent event) {
        var user = event.getAuthor();
        var channel = event.getChannel();
        var message = event.getMessage();
        var messageContent = message.getContentRaw();
        if (!user.isBot() && UserDao.has(user)) {
            var userData = UserDao.get(user);
            if (userData.hasWaiting(WaitingType.ANONYMOUS_MESSAGE)) {
                var waiting = (AnonymousWaiting) userData.getWaiting(WaitingType.ANONYMOUS_MESSAGE);
                var targetUser = waiting.getTargetUser();
                userData.removeWaiting(WaitingType.ANONYMOUS_MESSAGE);
                UserDao.clearUser(userData);
                SystemUtils.sendDirectMessageAnonymous(user, targetUser, messageContent, channel, message, false);
            }
        }
    }
}