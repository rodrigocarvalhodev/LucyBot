package net.rodrigocarvalho.lucy.type;

import lombok.var;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public enum PunishmentType {

    BAN {
        @Override
        public void execute(User user, Guild guild, String reason) {
            var controller = guild.getController();
            controller.ban(user, 7, reason).queue();
        }

        @Override
        public String getMessage() {
            return "Usuário banido com sucesso.";
        }
    },
    KICK {
        @Override
        public void execute(User user, Guild guild, String reason) {
            var controller = guild.getController();
            controller.kick(guild.getMember(user), reason).queue();
        }

        @Override
        public String getMessage() {
            return "Usuário expulso com sucesso.";
        }
    },
    UNBAN {
        @Override
        public void execute(User user, Guild guild, String reason) {
            var controller = guild.getController();
            controller.unban(user).queue();
        }

        @Override
        public String getMessage() {
            return "Usuário desbanido com sucesso.";
        }
    };

    public abstract void execute(User user, Guild guild, String reason);

    public abstract String getMessage();
}