package net.rodrigocarvalho.lucy.command;

import lombok.var;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.rodrigocarvalho.lucy.Lucy;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.type.DelayType;
import net.rodrigocarvalho.lucy.utils.BotUtils;
import net.rodrigocarvalho.lucy.utils.ReflectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Command extends ListenerAdapter {

    private final String PREFIX = ".";
    private final Set<AbstractCommand> COMMANDS = new HashSet<>();

    public Command() {
        for (var command : ReflectionUtils.getAllCommandss()) {
            COMMANDS.add(command);
            Lucy.print("Registred command " + command.getClass().getSimpleName());
        }
    }

    private AbstractCommand getCommand(String message) {
        String command = message.substring(PREFIX.length());
        return COMMANDS.stream().filter(x -> {
            var annotation = x.getClass().getAnnotation(CommandHandler.class);
            return annotation.name().equalsIgnoreCase(command) || contains(annotation.aliases(), command);
        }).findFirst().orElse(null);
    }

    private boolean contains(String[] array, String content) {
        for (String s : array) {
            if (s.equalsIgnoreCase(content)) return true;
        }
        return false;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        var user = event.getAuthor();
        if (user.isBot()) return;
        var member = event.getMember();
        var guild = event.getGuild();
        var channel = event.getChannel();
        var message = event.getMessage();
        var content = message.getContentRaw();

        if (content.startsWith(PREFIX)) {
            String[] split = content.split( " ");
            var command = getCommand(content.contains(" ") ? split[0] : content);
            if (command != null) {
                Lucy.print("[G] - " + user.getName() + ": " + content);
                if (BotUtils.hasDelay(user, DelayType.GLOBAL)) {
                    channel.sendMessage(user.getAsMention() + ", Opa! aguarde alguns segundos para executar o comando novamente.").queue();
                    return;
                }
                var annotation = command.getClass().getAnnotation(CommandHandler.class);
                if (annotation.rootCommand() && !BotUtils.isRootUser(user)) {
                    channel.sendMessage("Somente pessoas especiais podem usar esse comando :tux:.").queue();
                    return;
                }
                var permission = annotation.permission();
                if (permission != Permission.UNKNOWN && !member.hasPermission(permission)) {
                    channel.sendMessage(user.getAsMention() + ", você precisa da permissão " + permission.getName() + " para executar esse comando").queue();
                    return;
                }

                CommandEvent commandEvent = new CommandEvent(user, member, guild, channel, message, content.contains(" ") ? Arrays.copyOfRange(split, 1, split.length) : new String[0]);
                command.execute(commandEvent);
            }
        }
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        var user = event.getAuthor();
        if (user.isBot()) return;
        var channel = event.getChannel();
        var message = event.getMessage();
        var content = message.getContentRaw();

        if (content.startsWith(PREFIX)) {
            String[] split = content.split( " ");
            var command = getCommand(content.contains(" ") ? split[0] : content);
            if (command != null) {
                Lucy.print("[PM] - " + user.getName() + ": " + content);
                var annotation = command.getClass().getAnnotation(CommandHandler.class);
                if (annotation.rootCommand() && !BotUtils.isRootUser(user)) {
                    channel.sendMessage("Somente pessoas especiais podem usar esse comando :tux:.").queue();
                    return;
                }
                if (annotation.permission() != Permission.UNKNOWN) {
                    channel.sendMessage("Este comando é somente em grupos, pois requer a permissão " + annotation.permission().getName()).queue();
                    return;
                }
                if (BotUtils.hasDelay(user, DelayType.GLOBAL)) {
                    channel.sendMessage(user.getAsMention() + ", Opa! aguarde alguns segundos para executar o comando novamente.").queue();
                    return;
                }
                CommandEvent commandEvent = new CommandEvent(user, channel, message, content.contains(" ") ? Arrays.copyOfRange(split, 1, split.length) : new String[0]);
                command.execute(commandEvent);
            }
        }
    }
}