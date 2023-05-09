package com.duckdeveloper.lucy.event.events.punishment;

import com.duckdeveloper.lucy.event.model.AbstractEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

public class PunishmentReactionAdd extends AbstractEvent<ModalInteractionEvent> {

    @Override
    public void call(ModalInteractionEvent event) {
        if (!event.isFromGuild())
            return;

        // TODO: resolver isso.
//        var user = event.getUser();
//        var message = event.getMessageId();
//        var userData = UserProxy.get(user);
//        var channel = event.getChannel();
//        var emote = event.getReaction().getEmoji();
//        if (userData != null && userData.hasReaction(InteractionType.PUNISHMENT)) {
//            var reaction = (PunishmentInteraction) userData.getReaction(InteractionType.PUNISHMENT);
//            if (EmoteType.CHECK_MARK.is(emote) && reaction.getMessageId().equals(message)) {
//                userData.removeInteraction(InteractionType.PUNISHMENT);
//                try {
//                    var type = reaction.getPunishmentType();
//                    BotUtils.executePunishment(type, reaction.getTarget(),
//                            reaction.getGuild(), reaction.getReason());
//                    String messageToReceiver = type.getMessage();
//                    channel.sendMessage(user.getAsMention() + ", " + messageToReceiver).queue();
//                } catch (Exception e) {
//                    channel.sendMessage(user.getAsMention() + ", Parece que algum de nós ficou sem permissão.").queue();
//                }
//            }
//        }
    }
}