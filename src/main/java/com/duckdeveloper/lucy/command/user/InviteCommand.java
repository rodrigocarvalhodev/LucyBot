package com.duckdeveloper.lucy.command.user;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@CommandHandler(name = "invite", aliases = {"convite"})
public class InviteCommand extends AbstractCommand {

    private final JDA jda;

    @Lazy
    public InviteCommand(JDA jda) {
        this.jda = jda;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Aqui está meu convite! %s".formatted(jda.getInviteUrl(Permission.ADMINISTRATOR))).queue();
    }

    @Override
    public CommandData getCommandSlash() {
        return Commands.slash("invite", "Receber o convite da Lucy!");
    }
}