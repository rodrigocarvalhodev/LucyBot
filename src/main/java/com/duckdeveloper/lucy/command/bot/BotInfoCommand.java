package com.duckdeveloper.lucy.command.bot;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandEvent;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import com.duckdeveloper.lucy.utils.BotUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@CommandHandler(name = "botinfo")
public class BotInfoCommand extends AbstractCommand {

    private JDA jda;

    @Lazy
    public BotInfoCommand(JDA jda) {
        this.jda = jda;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var user = event.getUser();
        event.replyEmbeds(new EmbedBuilder()
                    .setTitle("Olá! Eu sou a Lucy <:tux:563133833155969034>")
                    .addField("<:Informacoes:563790538130587648> Guilds", String.valueOf(jda.getGuilds().size()), true)
                    .addField("<a:bounce:563789332515913737> Usuários", String.valueOf(jda.getUsers().size()), true)
                    .addField(":crown: Criador", "Rodrigo Carvalho", true)
                    .addField("<:uptime:563790462964596762> Uptime", BotUtils.getTime(), true)
                    .setFooter(user.getName(), user.getAvatarUrl())
                .build()
        ).queue();
    }

    @Override
    public CommandData getCommandSlash() {
        return Commands.slash("botinfo", "Veja as informações da Lucy");
    }
}