package com.duckdeveloper.lucy.type;

import net.dv8tion.jda.api.entities.emoji.EmojiUnion;

public enum EmoteType {

    ENVELOPE(":envelope_with_arrow:", "\uD83D\uDCE9", false),
    INFORMATION( ":information_desk_person:", "\uD83D\uDC81", false),
    CHECK_MARK( ":white_check_mark:", "✅", false),
    ONLINE( "<:online2:563527569652318218>", "", true),
    OFFLINE( "<:offline:563527847885406210>", "", true),
    ABSENT( "<:ausenteBRR:563527782232227851>", "", true),
    BUSY( "<:dnd:563527370334666768>", "", true);

    private String reaction;
    private String emote;
    private boolean custom;

    EmoteType(String reaction, String emote, boolean custom) {
        this.reaction = reaction;
        this.emote = emote;
        this.custom = custom;
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

    public boolean is(EmojiUnion emote) {
        return emote.getName().equals(this.emote);
    }
}