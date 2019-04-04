package net.rodrigocarvalho.lucy.command.moderation;

import lombok.var;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.rodrigocarvalho.lucy.Lucy;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.model.reactions.PunishmentReaction;
import net.rodrigocarvalho.lucy.type.CommandType;
import net.rodrigocarvalho.lucy.type.EmoteType;
import net.rodrigocarvalho.lucy.type.PunishmentType;
import net.rodrigocarvalho.lucy.utils.BotUtils;
import net.rodrigocarvalho.lucy.utils.ObjectUtils;

@CommandHandler(name = "ban", aliases = {"banir"}, permission = Permission.BAN_MEMBERS)
public class BanCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        if (!(event.getUsedChannel() instanceof TextChannel)) return;
        var user = event.getUser();
        var member = event.getMember();
        var channel = event.getUsedChannel();
        var args = event.getArgs();
        var guild = event.getGuild();
        var message = event.getMessage();
        if (ObjectUtils.asArguments(CommandType.BAN, user, channel, args)) {
            var targetUser = ObjectUtils.matchUser(message, args, 0);
            if (!ObjectUtils.validateUser(channel, user, targetUser, false)) return;
            var targetMember = guild.getMember(targetUser);
            if (targetMember == null) {
                event.sendMessage("Usuário não encontrado <:pinguim:563134096713318403>.");
                return;
            }

            if (!member.canInteract(targetMember)) {
                event.sendMessage("Você não possui permissão para banir este membro <:desesperado:563147487691407375>.");
                return;
            }

            if (!guild.getMember(Lucy.getJda().getSelfUser()).canInteract(targetMember)) {
                event.sendMessage("Eu não tenho permissão para banir este membro <:desesperado:563147487691407375>");
                return;
            }

            var reason = ObjectUtils.formatArguments(args, 1);
            var userData = ObjectUtils.getUserOrCreate(user);
            event.sendMessage("Tem certeza que deseja banir `" + targetUser.getName() + "`? Reaja com :white_check_mark: para banir.",
                    newMessage -> {
                        userData.addReaction(new PunishmentReaction(PunishmentType.BAN, user, guild, newMessage.getId(), targetUser, reason));
                        BotUtils.addReaction(newMessage, EmoteType.CHECK_MARK);
                    });
        }
    }
}