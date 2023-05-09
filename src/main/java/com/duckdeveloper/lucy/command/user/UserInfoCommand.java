package com.duckdeveloper.lucy.command.user;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandEvent;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import com.duckdeveloper.lucy.type.OnlineType;
import com.duckdeveloper.lucy.utils.TimeUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@CommandHandler(name = "userinfo", aliases = {"infouser"})
public class UserInfoCommand extends AbstractCommand {

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var user = event.getUser();
        var targetUserOption = event.getOption("target-user");
        var target = targetUserOption != null ? targetUserOption.getAsUser() : user;

        var guild = event.getGuild();
        var targetMember = guild.getMember(target);
        var game = "Desconhecido";
        var status = OnlineType.OFFLINE;
        String joined = null;
        if (targetMember != null) {
            game = this.getGame(targetMember);
            status = OnlineType.getByOnlineStatus(targetMember.getOnlineStatus());
            joined = TimeUtils.format(new Date(TimeUtils.getTime(targetMember.getTimeJoined())));
        }
        var created = TimeUtils.format(new Date(TimeUtils.getTime(target.getTimeCreated())));

        var createdFormat = ":sunny: Criação: " + created;
        var joinedFormat = ":atom: Entrou aqui em: " + joined;
        event.replyEmbeds(new EmbedBuilder()
                .setTitle(":bust_in_silhouette: Informações de " + target.getName())
                .addField(":desktop: Tag no Discord", target.getAsTag(), true)
                .addField(":desktop: ID do Discord", target.getId(), true)
                .addField(":video_game: Jogando", game, true)
                .addField("<a:typingstatus:563527924603682824> Status", status.getName() + " " + status.getType().getReaction(), true)
                .addField(":date: Datas", joined != null ? "%s\n%s".formatted(createdFormat, joinedFormat) : createdFormat, false)
                .setThumbnail(target.getAvatarUrl())
                .setFooter(user.getName(), user.getAvatarUrl())
                .build()
        ).queue();
    }

    @Override
    public CommandData getCommandSlash() {
        return Commands.slash("userinfo", "Obter informações de usuários")
                .addOption(OptionType.USER, "target-user", "Usuário para obter informações", false);
    }

    private String getGame(Member member) {
        var activities = member.getActivities();
        return !activities.isEmpty() ? activities.get(0).getName() : "Desconhecido";
    }


}