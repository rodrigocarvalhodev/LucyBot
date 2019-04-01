package net.rodrigocarvalho.lucy.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.utils.ReflectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Command extends ListenerAdapter {

    private final String PREFIX = ".";
    private final Set<AbstractCommand> COMMANDS = new HashSet<>();

    public Command() {
        COMMANDS.addAll(ReflectionUtils.getAllCommandss());
    }

    private AbstractCommand getCommand(String message) {
        String lowerMessage = message.toLowerCase();
        return COMMANDS.stream().filter(x -> {
            var annotation = x.getClass().getAnnotation(CommandHandler.class);
            return annotation.name().equalsIgnoreCase(lowerMessage) || Arrays.asList(annotation.aliases()).contains(lowerMessage);
        }).findFirst().orElse(null);
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        var user = event.getAuthor();
        var member = event.getMember();
        var guild = event.getGuild();
        var channel = event.getChannel();
        var message = event.getMessage();
        var content = message.getContentRaw();

        if (content.startsWith(PREFIX)) {
            var command = getCommand(content);
            if (command != null) {
                var annotation = command.getClass().getAnnotation(CommandHandler.class);
                var permission = annotation.permission();
                if (permission != Permission.UNKNOWN && member.hasPermission(permission)) {
                    if (!member.hasPermission(permission)) {
                        channel.sendMessage(user.getAsMention() + ", você precisa da permissão " + permission.getName() + " para executar esse comando").queue();
                        return;
                    }

                    String[] split = content.split(" ");
                    CommandEvent commandEvent = new CommandEvent(user, member, guild, channel, message, content.contains(" ") ? Arrays.copyOfRange(split, 1, split.length) : new String[]{});
                    command.execute(commandEvent);
                }
            }
        }
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        var user = event.getAuthor();
        var channel = event.getChannel();
        var message = event.getMessage();
        var content = message.getContentRaw();

        if (content.startsWith(PREFIX)) {
            var command = getCommand(content);
            if (command != null) {
                String[] split = content.split(" ");
                CommandEvent commandEvent = new CommandEvent(user, channel, message, content.contains(" ") ? Arrays.copyOfRange(split, 1, split.length) : new String[]{});
                command.execute(commandEvent);
            }
        }
    }
}