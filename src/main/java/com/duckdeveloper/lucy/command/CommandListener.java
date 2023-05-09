package com.duckdeveloper.lucy.command;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import com.duckdeveloper.lucy.utils.BotUtils;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CommandListener extends ListenerAdapter {

    private final Set<AbstractCommand> commands;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getUser().isBot())
            return;

        var abstractCommandOptional = this.getCommand(event);

        if (abstractCommandOptional.isPresent()) {
            var command = abstractCommandOptional.get();

            boolean isAvailableToRun = verify(event, command);

            if (isAvailableToRun)
                command.execute(event);
        }
    }

    private boolean verify(SlashCommandInteractionEvent event, AbstractCommand abstractCommand) {
        var member = event.getMember();
        var user = event.getUser();
        var commandHandler = abstractCommand.getClass().getAnnotation(CommandHandler.class);

        if (commandHandler.rootCommand() && !BotUtils.isRootUser(user)) {
            event.reply("Somente pessoas especiais podem usar esse comando <:tux:563133833155969034>.").queue();
            return false;
        }

        var permission = commandHandler.permission();
        if (permission != Permission.UNKNOWN && member != null && !member.hasPermission(permission)) {
            event.reply(user.getAsMention() + ", você precisa da permissão " + permission.getName() + " para executar esse comando").queue();
            return false;
        }
        return true;
    }

    protected Optional<AbstractCommand> getCommand(SlashCommandInteractionEvent event) {
        return this.commands.stream()
                .filter(abstractCommand -> this.checkIfIsCommand(event.getName(), abstractCommand))
                .findAny();
    }

    protected boolean checkIfIsCommand(String name, AbstractCommand abstractCommand) {
        var clazz = abstractCommand.getClass();
        var annotation = clazz.getAnnotation(CommandHandler.class);

        return annotation.name().equalsIgnoreCase(name);
    }
}