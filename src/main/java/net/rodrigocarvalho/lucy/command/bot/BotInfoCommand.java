package net.rodrigocarvalho.lucy.command.bot;

import lombok.var;
import net.dv8tion.jda.api.EmbedBuilder;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.utils.BotUtils;

@CommandHandler(name = "botinfo")
public class BotInfoCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        var user = event.getUser();
        event.sendMessage(new EmbedBuilder()
                    .setTitle("Olá! Eu sou a Lucy <:tux:563133833155969034>")
                    .addField("<:Informacoes:563790538130587648> Guilds", String.valueOf(jda.getGuilds().size()), true)
                    .addField("<a:bounce:563789332515913737> Usuários", String.valueOf(jda.getUsers().size()), true)
                    .addField(":crown: Criador", "Rodrigo Carvalho", true)
                    .addField("<:uptime:563790462964596762> Uptime", BotUtils.getTime(), true)
                    .setFooter(user.getName(), user.getAvatarUrl())
                .build()
        );
    }
}