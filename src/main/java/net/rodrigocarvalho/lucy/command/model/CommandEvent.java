package net.rodrigocarvalho.lucy.command.model;

import lombok.var;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;

import javax.security.auth.callback.Callback;
import java.util.function.Consumer;

public class CommandEvent {

    private User user;
    private Member member;
    private Guild guild;
    private TextChannel channel;
    private PrivateChannel privateChannel;
    private Message message;
    private String[] args;

    public CommandEvent(User user, Member member, Guild guild, TextChannel channel, Message message, String[] args) {
        this.user = user;
        this.member = member;
        this.guild = guild;
        this.channel = channel;
        this.message = message;
        this.args = args;
    }

    public CommandEvent(User user, PrivateChannel privateChannel, Message message, String[] args) {
        this.user = user;
        this.privateChannel = privateChannel;
        this.message = message;
        this.args = args;
    }

    public User getUser() {
        return user;
    }

    public Member getMember() {
        return member;
    }

    public Guild getGuild() {
        return guild;
    }

    public Message getMessage() {
        return message;
    }

    public TextChannel getChannel() {
        return channel;
    }

    public PrivateChannel getPrivateChannel() {
        return privateChannel;
    }

    public MessageChannel getUsedChannel() {
        return isFromPrivateChannel() ? privateChannel : channel;
    }

    public String[] getArgs() {
        return args;
    }

    public void sendMessage(String message) {
        getUsedChannel().sendMessage(user.getAsMention() + ", " + message).queue();
    }

    public void sendMessage(String message, Consumer<Message> callback) {
        getUsedChannel().sendMessage(user.getAsMention() + ", " + message).queue(callback);
    }

    public void sendMessage(Message message) {
        getUsedChannel().sendMessage(message).queue();
    }

    public void sendMessage(MessageEmbed embed) {
        var message = new MessageBuilder(user.getAsMention()).setEmbed(embed).build();
        getUsedChannel().sendMessage(message).queue();
    }

    public boolean isFromPrivateChannel() {
        return privateChannel != null;
    }
}