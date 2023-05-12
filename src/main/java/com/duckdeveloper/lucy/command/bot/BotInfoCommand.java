package com.duckdeveloper.lucy.command.bot;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
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
                    .setTitle("Olá! Eu sou a Lucy <:mytime:759000043582652456>")
                    .addField(":computer: Guilds", String.valueOf(jda.getGuilds().size()), true)
                    .addField(":bust_in_silhouette: Usuários", String.valueOf(jda.getUsers().size()), true)
                    .addField(":crown: Criador", "Rodrigo Carvalho", true)
                    .addField(":hourglass: Uptime", BotUtils.getTime(), true)
                    .setFooter(user.getName(), user.getAvatarUrl())
                .build()
        ).queue();
    }

    @Override
    public CommandData getCommandSlash() {
        return Commands.slash("botinfo", "Veja as informações da Lucy");
    }
}