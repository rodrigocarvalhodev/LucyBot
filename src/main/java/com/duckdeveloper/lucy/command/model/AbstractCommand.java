package com.duckdeveloper.lucy.command.model;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public abstract class AbstractCommand {

    public abstract void execute(SlashCommandInteractionEvent event);

    public abstract CommandData getCommandSlash();

}