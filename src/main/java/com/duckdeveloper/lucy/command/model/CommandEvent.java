package com.duckdeveloper.lucy.command.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.function.Consumer;

@AllArgsConstructor
@Getter
public class CommandEvent {

    private User user;
    private Member member;
    private Guild guild;
    private MessageChannelUnion channel;
    private Message message;
    private String[] args;

    public CommandEvent(User user, MessageChannelUnion channel, Message message, String[] args) {
        this.user = user;
        this.channel = channel;
        this.message = message;
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public void sendMessage(String message) {
        this.channel.sendMessage(user.getAsMention() + ", " + message).queue();
    }

    public void sendMessage(String message, Consumer<Message> callback) {
        this.channel.sendMessage(user.getAsMention() + ", " + message).queue(callback);
    }

    public void sendMessage(Message message) {
        this.channel.sendMessage(MessageCreateData.fromMessage(message)).queue();
    }

    public void sendMessage(MessageEmbed embed) {
        this.channel.sendMessageEmbeds(embed).queue();
    }

    public boolean isFromPrivateChannel() {
        return this.guild == null;
    }
}