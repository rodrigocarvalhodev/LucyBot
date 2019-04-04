package net.rodrigocarvalho.lucy.utils;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.NameSpace;
import bsh.UtilEvalError;
import lombok.var;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import net.rodrigocarvalho.lucy.factory.Delay;
import net.rodrigocarvalho.lucy.factory.UserData;
import net.rodrigocarvalho.lucy.model.reactions.AnonymousReaction;
import net.rodrigocarvalho.lucy.type.DelayType;
import net.rodrigocarvalho.lucy.type.EmoteType;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SystemUtils {

    private static final Runtime RUNTIME = Runtime.getRuntime();

    public static void sendDirectMessageAnonymous(User user, User targetUser, String anonymousMessage, MessageChannel channel, Message userMessage, boolean delay) {
        BotUtils.sendPrivateMessage(targetUser, channel,
                new MessageBuilder()
                        .setEmbed(
                                new EmbedBuilder()
                                        .setTitle("Mensagem anônima")
                                        .appendDescription("Você recebeu uma mensagem anônima!")
                                        .addField(EmoteType.ENVELOPE.getReaction() + " Mensagem", anonymousMessage, false)
                                        .addField(EmoteType.INFORMATION.getReaction() + " Observações", "Você pode responder a mensagem clicando no emoji!", false)
                                        .setFooter(targetUser.getName(), targetUser.getAvatarUrl())
                                        .build())
                        .build(),
                newMessage -> {
                    if (userMessage.getChannelType() == ChannelType.TEXT) {
                        userMessage.delete().queue();
                    }
                    BotUtils.addReaction(newMessage, EmoteType.ENVELOPE);
                    UserData data = ObjectUtils.getUserOrCreate(user);
                    UserData targetData = ObjectUtils.getUserOrCreate(targetUser);
                    if (delay) {
                        data.addDelay(new Delay(DelayType.ANONYMOUS_MESSAGE));
                    }
                    targetData.addReaction(new AnonymousReaction(user, newMessage.getId()));
                    channel.sendMessage(user.getAsMention() + ", Mensagem enviada com sucesso!").queue(message -> {
                        message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
                    });
                }
        );
    }

    public static List<String> eval(String code, User user, Message message, MessageChannel channel, Guild guild, String[] args) throws UtilEvalError, EvalError {
        var interpreter = new Interpreter();
        interpreter.setNameSpace(NameSpace.JAVACODE);
        var nameSpace = interpreter.getNameSpace();
        nameSpace.importPackage("net.rodrigocarvalho.lucy");
        nameSpace.importPackage("java.util");
        nameSpace.importPackage("java.net");
        nameSpace.importPackage("net.dv8tion.jda");
        nameSpace.setVariable("user", user, false);
        nameSpace.setVariable("message", message, false);
        nameSpace.setVariable("channel", channel, false);
        nameSpace.setVariable("guild", guild, false);
        nameSpace.setVariable("args", args, false);
        nameSpace.setVariable("output", interpreter, false);
        Object object = interpreter.eval(code);
        if (object == null && interpreter.get("retorno") != null) object = interpreter.get("retorno");
        String result = object != null ? object.toString() : "null";
        return BotUtils.getMessagesStripped(result);
    }

    public static List<String> bash(String command) throws IOException {
        var process = RUNTIME.exec(command);
        var message = ObjectUtils.formatInputStream(process.getInputStream());
        return BotUtils.getMessagesStripped(message);
    }
}