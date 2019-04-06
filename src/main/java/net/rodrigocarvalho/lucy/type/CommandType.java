package net.rodrigocarvalho.lucy.type;

import lombok.var;
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
                                .setTitle(":bust_in_silhouette: | DM anônima")
                                .setColor(Color.lightGray)
                                .setDescription(":interrobang: Como usar\n\n" +
                                        ".dma " + selfUser.getAsMention() + (" Uma frase aleatória\n" +
                                        ".dma " + selfUser.getId() + " Olá, mundo!"))
                                .addField(":wrench: | Alternativas",
                                        ".dmaanonima\n.dma", true)
                                .addField(":eyes: | Objetivo", "Envie mensagens diretas anonimamente para usuários", true)
                            .build()
                    ).build();
        }

        @Override
        public boolean validArguments(int length) {
            return length > 1;
        }
    },
    AVATAR {
        @Override
        public Message getHelp(User user) {
            var selfUser = Lucy.getJda().getSelfUser();
            return new MessageBuilder(user.getAsMention())
                    .setEmbed(
                            new EmbedBuilder()
                                    .setTitle(":bust_in_silhouette: | Avatar")
                                    .setColor(Color.lightGray)
                                    .setDescription(":interrobang: Como usar\n\n" +
                                            ".avatar " + selfUser.getAsMention() + "\n" +
                                            ".avatar " + selfUser.getId())
                                    .addField(":eyes: | Objetivo", "Ver a imagem de perfil de outros usuários", false)
                                    .build()
                    ).build();
        }

        @Override
        public boolean validArguments(int length) {
            return true;
        }
    },
    EVAL {
        @Override
        public Message getHelp(User user) {
            return new MessageBuilder(user.getAsMention() + ", Agora já pode digitar o código né meu  parsero.").build();
        }

        @Override
        public boolean validArguments(int length) {
            return length > 0;
        }
    },
    BASH {
        @Override
        public Message getHelp(User user) {
            return new MessageBuilder(user.getAsMention() + ", Agora já pode digitar o comando né meu parsero.").build();
        }

        @Override
        public boolean validArguments(int length) {
            return length > 0;
        }
    },
    BAN {
        @Override
        public Message getHelp(User user) {
            var selfUser = Lucy.getJda().getSelfUser();
            return new MessageBuilder(user.getAsMention())
                    .setEmbed(
                            new EmbedBuilder()
                                .setTitle(":bust_in_silhouette: | Banimento")
                                .setColor(Color.lightGray)
                                .setDescription(":interrobang: Como usar\n\n" +
                                        ".ban " + selfUser.getAsMention() + "\n" +
                                        ".ban " + selfUser.getAsMention() + " Fascista!")
                                .addField(":wrench: | Alternativas",
                                            ".ban\n.banir", true)
                                .addField(":eyes: | Objetivo", "Banir um usuário faxista.", true)
                            .build()
                    ).build();
        }

        @Override
        public boolean validArguments(int length) {
            return length > 0;
        }
    },
    KICK {
        @Override
        public Message getHelp(User user) {
            var selfUser = Lucy.getJda().getSelfUser();
            return new MessageBuilder(user.getAsMention())
                    .setEmbed(
                            new EmbedBuilder()
                                    .setTitle(":bust_in_silhouette: | Expulsão")
                                    .setColor(Color.lightGray)
                                    .setDescription(":interrobang: Como usar\n\n" +
                                            ".kick " + selfUser.getAsMention() + "\n" +
                                            ".kick " + selfUser.getAsMention() + " Fascista!")
                                    .addField(":wrench: | Alternativas",
                                            ".kick\n.expulsar", true)
                                    .addField(":eyes: | Objetivo", "Expulsar um usuário faxista.", true)
                                    .build()
                    ).build();
        }

        @Override
        public boolean validArguments(int length) {
            return length > 0;
        }
    },
    UNBAN {
        @Override
        public Message getHelp(User user) {
            var selfUser = Lucy.getJda().getSelfUser();
            return new MessageBuilder(user.getAsMention())
                    .setEmbed(
                            new EmbedBuilder()
                                    .setTitle(":bust_in_silhouette: | Desbanir")
                                    .setColor(Color.lightGray)
                                    .setDescription(":interrobang: Como usar\n\n" +
                                            ".kick " + selfUser.getAsMention() + "\n" +
                                            ".kick " + selfUser.getAsMention() + " Opressor!")
                                    .addField(":wrench: | Alternativas",
                                            ".kick\n.expulsar", true)
                                    .addField(":eyes: | Objetivo", "Perdoar um usuário faxista.", true)
                                    .build()
                    ).build();
        }

        @Override
        public boolean validArguments(int length) {
            return length > 0;
        }
    },
    LOAD_COMMAND {
        @Override
        public Message getHelp(User user) {
            return new MessageBuilder(user.getAsMention() + ", Utilize: `.loadcommand <path> <classenome>`").build();
        }

        @Override
        public boolean validArguments(int length) {
            return length > 1;
        }
    },
    UNLOAD_COMMAND {
        @Override
        public Message getHelp(User user) {
            return new MessageBuilder(user.getAsMention() + ", Utilize: `.unloadcommand <nome>`").build();
        }

        @Override
        public boolean validArguments(int length) {
            return length > 0;
        }
    };

    public abstract Message getHelp(User user);

    public abstract boolean validArguments(int length);
}
