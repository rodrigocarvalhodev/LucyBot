package net.rodrigocarvalho.lucy.type;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.MessageReaction;

import java.util.stream.Stream;

public enum EmoteType {

    ENVELOPE(CommandType.DIRECT_MESSAGE_ANONYMOUS, ":envelope_with_arrow:", "\\1f4e9"),
    INFORMATION(CommandType.DIRECT_MESSAGE_ANONYMOUS, ":information_desk_person:", "\\1f481");

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

    public boolean is(Emote emote) {
        return emote.getAsMention().equals(getReaction());
    }

    public static EmoteType[] getEmotesByCommandType(CommandType type) {
        return Stream.of(values()).filter(x -> x.getType() == type).toArray(EmoteType[]::new);
    }
}