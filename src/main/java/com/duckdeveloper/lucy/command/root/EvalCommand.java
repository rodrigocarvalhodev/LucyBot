package com.duckdeveloper.lucy.command.root;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import com.duckdeveloper.lucy.utils.SystemUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

@Component
@CommandHandler(name = "eval", rootCommand = true)
public class EvalCommand extends AbstractCommand {

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var user = event.getUser();
        var code = event.getOption("code").getAsString();
        try {
            long millis = System.currentTimeMillis();
            var result = SystemUtils.eval(code, user, event.getMessageChannel(), event.getGuild());
            long executionTimeInMilliseconds = System.currentTimeMillis() - millis;

            if (result.size() > 1) {
                event.reply("Oxi, que mensagem gigante ein? <:thinkrage:563142958938062849> Irei corta-la.").queue();
            }

            var page = 0;
            for (var content : result) {
                page++;
                event.replyEmbeds(
                        new EmbedBuilder()
                                .setTitle("Terminamos.")
                                .addField("Resultado", content, false)
                                .addField("Tempo de execução", String.valueOf(executionTimeInMilliseconds), false)
                                .setFooter("Página %d".formatted(page), user.getAvatarUrl())
                                .build()
                ).queue();
            }
        } catch (Exception e) {
            StackTraceElement element = e.getStackTrace()[0];
            event.replyEmbeds(
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
