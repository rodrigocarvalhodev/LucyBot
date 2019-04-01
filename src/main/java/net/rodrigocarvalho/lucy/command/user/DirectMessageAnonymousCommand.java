package net.rodrigocarvalho.lucy.command.user;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.type.CommandType;
import net.rodrigocarvalho.lucy.type.EmoteType;
import net.rodrigocarvalho.lucy.utils.BotUtils;
import net.rodrigocarvalho.lucy.utils.ObjectUtils;

@CommandHandler (name = "dmanonima", aliases = {"dma", "anonymousdm"})
public class DirectMessageAnonymousCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        var user = event.getUser();
        var channel = event.getUsedChannel();
        var message = event.getMessage();
        var args = event.getArgs();
        if (ObjectUtils.asArguments(CommandType.DIRECT_MESSAGE_ANONYMOUS, user, channel, args)) {
            var targetUser = ObjectUtils.matchUser(message, args, 0);
            if (!ObjectUtils.validateUser(channel, user, targetUser)) return;
            var anonymousMessage = ObjectUtils.formatArguments(args, 1);
            var newMessage = BotUtils.sendPrivateMessage(targetUser, channel,
                    new MessageBuilder()
                    .setEmbed(
                        new EmbedBuilder()
                            .setTitle("Mensagem anônima")
                            .appendDescription("Você recebeu uma mensagem anônima!")
                            .addField(EmoteType.ENVELOPE.getReaction() + " Mensagem", anonymousMessage, false)
                            .addField(EmoteType.INFORMATION.getReaction() + " Observações", "Você pode responder a mensagem clicando no emoji!", false)
                            .setFooter(targetUser.getName(), targetUser.getAvatarUrl())
                        .build())
                    .build()
            );
            BotUtils.addReaction(newMessage, EmoteType.ENVELOPE);
        }
    }
}