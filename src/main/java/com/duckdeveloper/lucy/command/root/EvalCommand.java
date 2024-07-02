package com.duckdeveloper.lucy.command.root;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import com.duckdeveloper.lucy.service.BashService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@CommandHandler(name = "eval", rootCommand = true)
public class EvalCommand extends AbstractCommand {

    private final BashService bashService;

    @Lazy
    public EvalCommand(BashService bashService) {
        this.bashService = bashService;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        var user = event.getUser();
        var eventHook = event.getHook();
        var code = event.getOption("code").getAsString();
        try {
            long millis = System.currentTimeMillis();
            var result = this.bashService.eval(code, user, event.getMessageChannel(), event.getGuild());
            long executionTimeInMilliseconds = System.currentTimeMillis() - millis;

            if (result.size() > 1) {
                eventHook.sendMessage("Oxi, que mensagem gigante ein? <:thinkrage:563142958938062849> Irei corta-la.").queue();
            }

            var page = 0;
            for (var content : result) {
                page++;
                eventHook.sendMessageEmbeds(
                        new EmbedBuilder()
                                .setTitle("Finalizado.")
                                .addField("Resultado", content, false)
                                .addField("Tempo de execução", String.valueOf(executionTimeInMilliseconds), false)
                                .setFooter("Página %d".formatted(page), user.getAvatarUrl())
                                .build()
                ).queue();
            }
        } catch (Exception e) {
            StackTraceElement element = e.getStackTrace()[0];
            eventHook.sendMessageEmbeds(
                    new EmbedBuilder()
                            .setTitle("Opa! parece que algo deu errado <:desesperado:563147487691407375>")
                            .addField("Erro", e.getMessage(), false)
                            .addField("Linha", "%s:%d".formatted(element.getClassName(), element.getLineNumber()), false)
                            .build()
            ).queue();
        }
    }

    @Override
    public CommandData getCommandSlash() {
        return Commands.slash("eval", "Execute códigos Java por aqui")
                .addOption(OptionType.STRING, "code", "Código", true);
    }
}
