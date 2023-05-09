package com.duckdeveloper.lucy.command.guild;

import com.duckdeveloper.lucy.utils.TimeUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@CommandHandler(name = "serverinfo", aliases = {"guildinfo", "infoserver", "ginfo", "sinfo"})
public class ServerInfoCommand extends AbstractCommand {

    private final JDA jda;

    @Lazy
    public ServerInfoCommand(JDA jda) {
        this.jda = jda;
    }


    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var user = event.getUser();
        var serverId = event.getOption("server-id");
        var guild = serverId != null ? jda.getGuildById(String.valueOf(serverId)) : event.getGuild();

        if (guild == null) {
            event.reply("Não consegui encontrar esse servidor <:pinguim:563134096713318403>").queue();
            return;
        }

        var lucyMember = guild.getMember(jda.getSelfUser());
        var created = TimeUtils.format(new Date(TimeUtils.getTime(guild.getTimeCreated())));
        var joined = TimeUtils.format(new Date(TimeUtils.getTime(lucyMember.getTimeJoined())));

        var createdFormat = ":sunny: Criação: " + created;
        var joinedFormat = ":atom: Entrou aqui em: " + joined;

        event.replyEmbeds(new EmbedBuilder()
                    .setTitle(guild.getName())
                    .setThumbnail(guild.getIconUrl())
                    .addField(":crown: Dono", guild.getOwner().getUser().getName(), true)
                    .addField("<a:bounce:563789332515913737> Membros", String.valueOf(guild.getMembers().size()), true)
                    .addField(":desktop: ID", guild.getId(), true)
                    .addField(":bar_chart: Canais", String.valueOf(guild.getChannels().size()), true)
                    .addField(":gem: Emojis", String.valueOf(guild.getEmojis().size()), true)
                    .addField(":date: Datas", createdFormat + "\n" + joinedFormat, false)
                    .setFooter(user.getName(), user.getAvatarUrl())
                .build()
        ).queue();
    }

    @Override
    public CommandData getCommandSlash() {
        return Commands.slash("serverinfo", "Obtenha informações de um servidor que eu estou nele.")
                .addOption(OptionType.STRING, "server-id", "ID do servidor que deseja obter informações", false);
    }
}