package net.rodrigocarvalho.lucy.command.moderation;

import lombok.var;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.model.reactions.PunishmentReaction;
import net.rodrigocarvalho.lucy.type.CommandType;
import net.rodrigocarvalho.lucy.type.EmoteType;
import net.rodrigocarvalho.lucy.type.PunishmentType;
import net.rodrigocarvalho.lucy.utils.BotUtils;
import net.rodrigocarvalho.lucy.utils.ObjectUtils;

@CommandHandler(name = "unban", aliases = {"desbanir"}, permission = Permission.BAN_MEMBERS)
public class UnbanCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        if (!(event.getUsedChannel() instanceof TextChannel)) return;
        var user = event.getUser();
        var channel = event.getUsedChannel();
        var args = event.getArgs();
        var guild = event.getGuild();
        var message = event.getMessage();
        if (ObjectUtils.asArguments(CommandType.KICK, user, channel, args)) {
            var targetUser = ObjectUtils.matchUser(message, args, 0);
            if (!ObjectUtils.validateUser(channel, user, targetUser, false)) return;
            var userData = ObjectUtils.getUserOrCreate(user);
            try {
                var ban = guild.retrieveBanById(targetUser.getId()).complete();
                if (ban == null) {
                    event.sendMessage("Este usuário não está banido.");
                    return;
                }
                event.sendMessage("Tem certeza que deseja desbanir `" + targetUser.getName() + "`? Reaja com :white_check_mark: para executar.",
                        newMessage -> {
                            userData.addReaction(new PunishmentReaction(PunishmentType.UNBAN, user, guild, newMessage.getId(), targetUser, null));
                            BotUtils.addReaction(newMessage, EmoteType.CHECK_MARK);
                        });
            } catch (Exception e) {
                event.sendMessage("Este usuário não está banido.");
            }
        }
    }
}