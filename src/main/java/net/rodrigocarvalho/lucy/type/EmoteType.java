package net.rodrigocarvalho.lucy.type;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.MessageReaction;

import java.util.stream.Stream;

public enum EmoteType {

    ENVELOPE(CommandType.DIRECT_MESSAGE_ANONYMOUS, ":envelope_with_arrow:", "\uD83D\uDCE9", false),
    INFORMATION(CommandType.DIRECT_MESSAGE_ANONYMOUS, ":information_desk_person:", "\uD83D\uDC81", false),
    CHECK_MARK(CommandType.BAN, ":white_check_mark:", "✅", false);

    private CommandType type;
    private String reaction;
    private String emote;
    private boolean custom;

    private EmoteType(CommandType type, String reaction, String emote, boolean custom) {
        this.type = type;
        this.reaction = reaction;
        this.emote = emote;
        this.custom = custom;
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

    public boolean isCustom() {
        return custom;
    }

    public boolean is(MessageReaction.ReactionEmote emote) {
        return emote.getName().equals(this.emote);
    }

    public static EmoteType[] getEmotesByCommandType(CommandType type) {
        return Stream.of(values()).filter(x -> x.getType() == type).toArray(EmoteType[]::new);
    }
}