package com.duckdeveloper.lucy.command.root;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import com.duckdeveloper.lucy.utils.SystemUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@CommandHandler(name = "bash", rootCommand = true)
public class BashCommand extends AbstractCommand {

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var user = event.getUser();
        var command = event.getOption("command").getAsString();
        try {
            var bash = SystemUtils.bash(command);
            if (bash.size() > 1) {
                event.reply("Oxi, que mensagem gigante ein? <:thinkrage:563142958938062849> Irei corta-la.").queue();
            }

            var i = 0;
            for (var content : bash) {
                i++;
                event.replyEmbeds(
                        new EmbedBuilder()
                                .setTitle("Bash")
                                .setDescription("Resultado: \n\n" + content)
                                .setFooter("Página " + i, user.getAvatarUrl())
                                .build()
                ).queue();
            }
        } catch (IOException e) {
            StackTraceElement element = e.getStackTrace()[0];
            event.replyEmbeds(
                    new EmbedBuilder()
                            .setTitle("Opa! parece que algo deu errado <:desesperado:563147487691407375>")
                            .addField("Erro", e.getMessage(), false)
                            .addField("Linha", element.getClassName() + ":" + element.getLineNumber(), false)
                            .build()
            ).queue();
        }
    }

    @Override
    public CommandData getCommandSlash() {
        return Commands.slash("bash", "Sei lá");
    }
}
