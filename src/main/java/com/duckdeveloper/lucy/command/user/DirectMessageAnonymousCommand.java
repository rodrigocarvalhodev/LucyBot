package com.duckdeveloper.lucy.command.user;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import com.duckdeveloper.lucy.service.AnonymousDirectMessageService;
import com.duckdeveloper.lucy.service.DelayService;
import com.duckdeveloper.lucy.type.DelayType;
import com.duckdeveloper.lucy.utils.ObjectUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@CommandHandler(name = "dmanonima", aliases = {"dma", "anonymousdm"})
public class DirectMessageAnonymousCommand extends AbstractCommand {

    private final JDA jda;

    private final AnonymousDirectMessageService anonymousDirectMessageService;
    private final DelayService delayService;

    @Lazy
    public DirectMessageAnonymousCommand(JDA jda, AnonymousDirectMessageService anonymousDirectMessageService, DelayService delayService) {
        this.jda = jda;
        this.anonymousDirectMessageService = anonymousDirectMessageService;
        this.delayService = delayService;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.getInteraction().getHook().deleteOriginal().queue();
        var user = event.getUser();

        var targetUser = event.getOption("target-user").getAsUser();
        var message = event.getOption("message").getAsString();

        if (delayService.hasDelay(user, DelayType.ANONYMOUS_MESSAGE)) {
            event.reply("Opa! Aguarde alguns segundos para executar este comando novamente.").queue();
            return;
        }

        if (!ObjectUtils.isUserValid(jda.getSelfUser(), user, targetUser, true)) {
            event.reply("Certifique-se que informou um usuário válido.").queue();
            return;
        }

        if (!ObjectUtils.validateMessage(message)) {
            event.reply("Opa! digite uma mensagem válida.").queue();
            return;
        }

        this.anonymousDirectMessageService.sendAnonymousMessage(user, targetUser, message);
        event.reply("Mensagem enviada com sucesso!")
                .setEphemeral(true)
                .queue();
    }

    @Override
    public CommandData getCommandSlash() {
        return Commands.slash("dmanonima", "Enviar mensagem anônima a algum usuário")
                .addOption(OptionType.USER, "target-user", "Usuário que deseja enviar a mensagem anônima.", true)
                .addOption(OptionType.STRING, "message", "Mensagem que deseja enviar", true);
    }
}