package com.duckdeveloper.lucy.command.root;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import com.duckdeveloper.lucy.service.BashService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;

import static java.text.MessageFormat.format;


@Component
@CommandHandler(name = "bash", rootCommand = true)
@Slf4j
public class BashCommand extends AbstractCommand {

    private final BashService bashService;

    @Lazy
    public BashCommand(BashService bashService) {
        this.bashService = bashService;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        var user = event.getUser();
        var command = event.getOption("command").getAsString();
        var eventHook = event.getHook();
        try {
            var bash = this.bashService.executeBash(command);
            var bashSize = bash.size();
            if (bashSize > 1) {
                eventHook.sendMessage("Oxi, que mensagem gigante ein? <:thinkrage:563142958938062849> Irei corta-la.").queue();
            }

            int i = 0;
            for (var content : bash) {
                i++;
                eventHook.sendMessageEmbeds(
                        new EmbedBuilder()
                                .setTitle(format("Bash - Página {0}/{1}", i, bashSize))
                                .setDescription("Resultado: \n\n" + content)
                                .setAuthor(user.getName(), user.getAvatarUrl())
                                .setFooter(user.getName(), user.getAvatarUrl())
                                .setColor(Color.ORANGE)
                                .build()
                ).queue();
            }
        } catch (IOException e) {
            StackTraceElement element = e.getStackTrace()[0];
            eventHook.sendMessageEmbeds(
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
        return Commands.slash("bash", "Executar comandos no terminal.")
                .addOption(OptionType.STRING, "command", "Comando a ser executado", true);
    }
}
