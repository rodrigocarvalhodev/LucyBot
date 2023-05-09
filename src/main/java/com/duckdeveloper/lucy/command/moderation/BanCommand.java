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
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;


@Component
@CommandHandler(name = "ban", aliases = {"banir"}, permission = Permission.BAN_MEMBERS)
public class BanCommand extends AbstractCommand {

    private final JDA jda;

    private final UserProxy userProxy;

    @Lazy
    public BanCommand(JDA jda, UserProxy userProxy) {
        this.jda = jda;
        this.userProxy = userProxy;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var user = event.getUser();
        var member = event.getMember();
        var guild = event.getGuild();

        var targetUser = requireNonNull(event.getOption("target-user")).getAsUser();

        var jdaUser = jda.getSelfUser();

        if (!ObjectUtils.isUserValid(jdaUser, user, targetUser, false)) {
            event.reply("Certifique-se que digitou um usuário válido.").queue();
            return;
        }

        var targetMember = guild.getMember(targetUser);
        if (targetMember == null) {
            event.reply("Usuário não encontrado <:pinguim:563134096713318403>.").queue();
            return;
        }

        if (!member.canInteract(targetMember)) {
            event.reply("Você não possui permissão para banir este membro <:desesperado:563147487691407375>.").queue();
            return;
        }

        if (!guild.getMember(jdaUser).canInteract(targetMember)) {
            event.reply("Eu não tenho permissão para banir este membro <:desesperado:563147487691407375>").queue();
            return;
        }

        var reasonOption = event.getOption("reason");
        var reason = reasonOption != null ? reasonOption.getAsString() : "Sem motivo";

        var userData = userProxy.getUserDataOrCreate(user);

        var yesButton = Button.success("yes", "Sim");
        var noButton = Button.danger("no", "Não");

        event.replyModal(
                Modal.create("banAnswer", "Tem certeza que deseja banir o usuário %s ?".formatted(targetUser.getName()))
                        .addActionRow(yesButton, noButton)
                        .build()
        ).queue();

//        event.reply("Tem certeza que deseja banir `" + targetUser.getName() + "`? Reaja com :white_check_mark: para banir.")
//                .queue(newMessage -> {
//                    userData.addReaction(new PunishmentReaction(PunishmentType.BAN, user, guild, newMessage.getId(), targetUser, reason));
//                    BotUtils.addReaction(newMessage, EmoteType.CHECK_MARK);
//                }).queue();
    }

    @Override
    public CommandData getCommandSlash() {
        var commandHandler = getClass().getAnnotation(CommandHandler.class);
        return Commands.slash("ban", "Banir um membro do servidor")
                .addOption(OptionType.USER, "target-user", "Usuário a ser banido", true)
                .addOption(OptionType.STRING, "reason", "Razão do banimento", false)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(commandHandler.permission()))
                .setGuildOnly(true);
    }
}