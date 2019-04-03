package net.rodrigocarvalho.lucy.type;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.MessageReaction;

import java.util.stream.Stream;

public enum EmoteType {

    ENVELOPE(CommandType.DIRECT_MESSAGE_ANONYMOUS, ":envelope_with_arrow:", "\uD83D\uDCE9"),
    INFORMATION(CommandType.DIRECT_MESSAGE_ANONYMOUS, ":information_desk_person:", "\uD83D\uDC81");

    private CommandType type;
    private String reaction;
    private String emote;

    private EmoteType(CommandType type, String reaction, String emote) {
        this.type = type;
        this.reaction = reaction;
        this.emote = emote;
    }

    public CommandType getType() {
        return type;
    }

    public String getReaction() {
        return reaction;
    }

    public String getEmote() {
        return emote;
    }

    public boolean is(MessageReaction.ReactionEmote emote) {
        return emote.getName().equals(this.emote);
    }

    public static EmoteType[] getEmotesByCommandType(CommandType type) {
        return Stream.of(values()).filter(x -> x.getType() == type).toArray(EmoteType[]::new);
    }
}