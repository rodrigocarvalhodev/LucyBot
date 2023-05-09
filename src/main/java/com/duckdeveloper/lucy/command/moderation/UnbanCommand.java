package com.duckdeveloper.lucy.command.moderation;

import com.duckdeveloper.lucy.proxy.UserProxy;
import com.duckdeveloper.lucy.utils.ObjectUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@CommandHandler(name = "unban", aliases = {"desbanir"}, permission = Permission.BAN_MEMBERS)
public class UnbanCommand extends AbstractCommand {

    private final JDA jda;

    private final UserProxy userProxy;

    @Lazy
    public UnbanCommand(JDA jda, UserProxy userProxy) {
        this.jda = jda;
        this.userProxy = userProxy;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var user = event.getUser();
        var guild = event.getGuild();
        var targetUser = event.getOption("target-user").getAsUser();
        var selfUser = jda.getSelfUser();
        if (!ObjectUtils.isUserValid(selfUser, user, targetUser, false)) {
            event.reply("Certifique-se que digitou um usuário válido.").queue();
            return;
        }
        var userData = userProxy.getUserDataOrCreate(user);

        try {
            var ban = guild.retrieveBan(targetUser).complete();
            if (ban == null) {
                event.reply("Este usuário não está banido.").queue();
                return;
            }

            // TODO: completar comando.
        } catch (Exception e) {
            event.reply("Este usuário não está banido.").queue();
        }
    }

    @Override
    public CommandData getCommandSlash() {
        var commandHandler = getClass().getAnnotation(CommandHandler.class);
        return Commands.slash("unban", "Desbanir um membro do servidor")
                .addOption(OptionType.USER, "target-user", "Usuário a ser desbanido", true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(commandHandler.permission()))
                .setGuildOnly(true);
    }
}