package net.rodrigocarvalho.lucy.command.user;

import lombok.var;
import net.dv8tion.jda.api.EmbedBuilder;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.type.OnlineType;
import net.rodrigocarvalho.lucy.utils.ObjectUtils;
import net.rodrigocarvalho.lucy.utils.TimeUtils;

import java.util.Date;

@CommandHandler(name = "userinfo", aliases = {"infouser"})
public class UserInfoCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        var user = event.getUser();
        var args = event.getArgs();
        var message = event.getMessage();
        var target = args.length > 0 ? ObjectUtils.matchUser(message, args, 0) : user;

        if (target == null) {
            event.sendMessage("Não consegui encontrar esse usuário <:pinguim:563134096713318403>");
            return;
        }

        var guild = event.getGuild();
        var targetMember = guild.getMember(target);
        var game = targetMember != null && targetMember.getActivities().size() > 0 ? targetMember.getActivities().get(0).getName() : "Desconhecido";
        var status = targetMember != null ? OnlineType.getByOnlineStatus(targetMember.getOnlineStatus()) : OnlineType.OFFLINE;
        var created = TimeUtils.format(new Date(TimeUtils.getTime(user.getTimeCreated())));
        var joined = targetMember != null ? TimeUtils.format(new Date(TimeUtils.getTime(targetMember.getTimeJoined()))) : null;

        var createdFormat = ":sunny: Criação: " + created;
        var joinedFormat = ":atom: Entrou aqui em: " + joined;
        event.sendMessage(new EmbedBuilder()
                .setTitle(":bust_in_silhouette: Informações de " + target.getName())
                .addField(":desktop: Tag no Discord", user.getAsTag(), true)
                .addField(":desktop: ID do Discord", target.getId(), true)
                .addField(":video_game: Jogando", game, true)
                .addField("<a:typingstatus:563527924603682824> Status", status.getName() + " " + status.getType().getReaction(), true)
                .addField(":date: Datas", joined != null ? createdFormat + "\n" + joinedFormat : createdFormat, false)
                .setThumbnail(target.getAvatarUrl())
                .setFooter(user.getName(), user.getAvatarUrl())
        .build());
    }
}