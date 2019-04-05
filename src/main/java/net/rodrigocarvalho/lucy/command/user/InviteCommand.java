package net.rodrigocarvalho.lucy.command.user;

import net.dv8tion.jda.api.Permission;
import net.rodrigocarvalho.lucy.Lucy;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;

@CommandHandler(name = "invite", aliases = {"convite"})
public class InviteCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        event.sendMessage("Aqui está meu convite! " + jda.getInviteUrl(Permission.ADMINISTRATOR));
    }
}