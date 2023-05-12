package com.duckdeveloper.lucy.command.user;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

@Component
@CommandHandler(name = "avatar")
public class AvatarCommand extends AbstractCommand {

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var user = event.getUser();
        var userOption = event.getOption("target-user");

        var targetUser = userOption != null ? userOption.getAsUser() : user;

        event.replyEmbeds(
                new EmbedBuilder()
                    .setTitle("Avatar de %s".formatted(targetUser.getName()))
                    .setImage(targetUser.getAvatarUrl().concat("?size=2048"))
                    .setFooter(user.getName(), user.getAvatarUrl())
                .build()
        ).queue();
    }

    @Override
    public CommandData getCommandSlash() {
        return Commands.slash("avatar", "Veja o seu avatar ou o de outro usuário!")
                .addOption(OptionType.USER, "target-user", "Usuário que deseja visualizar o avatar", false);
    }
}
