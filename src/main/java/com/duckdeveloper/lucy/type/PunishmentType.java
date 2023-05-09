package com.duckdeveloper.lucy.type;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public enum PunishmentType {

    BAN {
        @Override
        public void execute(User user, Guild guild, String reason) {
            guild.ban(user, 1000000, TimeUnit.DAYS).queue();
        }

        @Override
        public String getMessage() {
            return "Usuário banido com sucesso.";
        }
    },
    KICK {
        @Override
        public void execute(User user, Guild guild, String reason) {
            guild.kick(Objects.requireNonNull(guild.getMember(user)), reason).queue();
        }

        @Override
        public String getMessage() {
            return "Usuário expulso com sucesso.";
        }
    },
    UNBAN {
        @Override
        public void execute(User user, Guild guild, String reason) {
            guild.unban(user).queue();
        }

        @Override
        public String getMessage() {
            return "Usuário desbanido com sucesso.";
        }
    };

    public abstract void execute(User user, Guild guild, String reason);

    public abstract String getMessage();
}