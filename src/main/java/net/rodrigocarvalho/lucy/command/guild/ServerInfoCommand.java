package net.rodrigocarvalho.lucy.command.guild;

import lombok.var;
import net.dv8tion.jda.api.EmbedBuilder;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.utils.TimeUtils;

import java.util.Date;

@CommandHandler(name = "serverinfo", aliases = {"guildinfo", "infoserver", "ginfo", "sinfo"})
public class ServerInfoCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        var user = event.getUser();
        var args = event.getArgs();
        var guild = args.length > 0 ? jda.getGuildById(args[0]) : event.getGuild();

        if (guild == null) {
            event.sendMessage("Não consegui encontrar essa guild <:pinguim:563134096713318403>");
            return;
        }

        var lucyMember = guild.getMember(jda.getSelfUser());
        var created = TimeUtils.format(new Date(TimeUtils.getTime(guild.getTimeCreated())));
        var joined = TimeUtils.format(new Date(TimeUtils.getTime(lucyMember.getTimeJoined())));

        var createdFormat = ":sunny: Criação: " + created;
        var joinedFormat = ":atom: Entrou aqui em: " + joined;

        event.sendMessage(new EmbedBuilder()
                    .setTitle(guild.getName())
                    .setThumbnail(guild.getIconUrl())
                    .addField(":crown: Dono", guild.getOwner().getUser().getName(), true)
                    .addField(":map: Região", guild.getRegion().getName() + " " + guild.getRegion().getEmoji(), true)
                    .addField("<a:bounce:563789332515913737> Membros", String.valueOf(guild.getMembers().size()), true)
                    .addField(":desktop: ID", guild.getId(), true)
                    .addField(":bar_chart: Canais", String.valueOf(guild.getChannels().size()), true)
                    .addField(":gem: Emojis", String.valueOf(guild.getEmotes().size()), true)
                    .addField(":date: Datas", createdFormat + "\n" + joinedFormat, false)
                    .setFooter(user.getName(), user.getAvatarUrl())
                .build()
        );
    }
}
