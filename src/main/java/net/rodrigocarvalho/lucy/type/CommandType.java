package net.rodrigocarvalho.lucy.type;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.rodrigocarvalho.lucy.Lucy;

import java.awt.*;

public enum CommandType {

    DIRECT_MESSAGE_ANONYMOUS {
        @Override
        public Message getHelp(User user) {
            var selfUser = Lucy.getJda().getSelfUser();
            return new MessageBuilder(user.getAsMention())
                    .setEmbed(
                            new EmbedBuilder()
                                .setTitle("Uso do comando.")
                                .setColor(Color.GRAY)
                                .appendDescription("**Mensagem anônima: .dmanonima**\n")
                                .appendDescription("Envie uma mensagem anônima a um usuário!\n")
                                .addField("Exemplos",
                                        ".dmanonima\n."
                                        +     ".dmanonima " + selfUser.getAsMention() + "\n"
                                        +     ".dmanonima " + selfUser.getId(), false)
                                .addField("Comandos possíveis",
                                        ".dmaanonima\n.dma", false)
                                .setAuthor("Tutorial de comando - " + user.getName(), user.getAvatarUrl())
                            .build()
                    ).build();
        }

        @Override
        public boolean validArguments(int length) {
            return length > 1;
        }
    };

    public abstract Message getHelp(User user);

    public abstract boolean validArguments(int length);
}