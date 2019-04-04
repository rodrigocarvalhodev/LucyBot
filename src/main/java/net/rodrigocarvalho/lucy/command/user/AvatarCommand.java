package net.rodrigocarvalho.lucy.command.user;

import lombok.var;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.utils.ObjectUtils;

@CommandHandler(name = "avatar")
public class AvatarCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        var user = event.getUser();
        var args = event.getArgs();
        var message = event.getMessage();

        User target;
        if (args.length == 0) target = user;
        else target = ObjectUtils.matchUser(message, args, 0);

        if (target == null) {
            event.sendMessage("Não consegui encontrar esse usuário :frowning2:");
            return;
        }
        event.sendMessage(
                new EmbedBuilder()
                    .setTitle("Avatar de " + target.getName())
                    .setImage(target.getAvatarUrl() + "?size=2048")
                    .setFooter("Comando executado por " + user.getName(), user.getAvatarUrl())
                .build()
        );
    }
}
