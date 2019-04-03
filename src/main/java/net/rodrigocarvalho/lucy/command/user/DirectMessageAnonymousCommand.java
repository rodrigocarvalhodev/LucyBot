package net.rodrigocarvalho.lucy.command.user;

import lombok.var;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.dao.UserDao;
import net.rodrigocarvalho.lucy.type.CommandType;
import net.rodrigocarvalho.lucy.type.DelayType;
import net.rodrigocarvalho.lucy.utils.ObjectUtils;
import net.rodrigocarvalho.lucy.utils.SystemUtils;

@CommandHandler (name = "dmanonima", aliases = {"dma", "anonymousdm"})
public class DirectMessageAnonymousCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        var user = event.getUser();
        var channel = event.getUsedChannel();
        var message = event.getMessage();
        var args = event.getArgs();
        if (ObjectUtils.asArguments(CommandType.DIRECT_MESSAGE_ANONYMOUS, user, channel, args)) {
            var userData = UserDao.get(user);
            if (userData != null && userData.hasDelay(DelayType.ANONYMOUS_MESSAGE)) {
                if (!userData.getDelay(DelayType.ANONYMOUS_MESSAGE).isExpired()) {
                    event.sendMessage("Opa! Aguarde alguns segundos para executar este comando novamente.");
                    return;
                }
                userData.removeDelay(DelayType.ANONYMOUS_MESSAGE);
                UserDao.clearUser(userData);
            }
            var targetUser = ObjectUtils.matchUser(message, args, 0);
            if (!ObjectUtils.validateUser(channel, user, targetUser)) return;
            var anonymousMessage = ObjectUtils.formatArguments(args, 1);
            if (!ObjectUtils.validateMessage(anonymousMessage, user, channel)) return;
            SystemUtils.sendDirectMessageAnonymous(user, targetUser, anonymousMessage, channel, message, true);
        }
    }
}