package com.duckdeveloper.lucy.utils;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.NameSpace;
import bsh.UtilEvalError;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.io.IOException;
import java.util.List;

public class SystemUtils {

    private static final Runtime RUNTIME = Runtime.getRuntime();

    public static List<String> eval(String code, User user, MessageChannel channel, Guild guild) throws UtilEvalError, EvalError {
        var interpreter = new Interpreter();
        interpreter.setNameSpace(NameSpace.JAVACODE);
        var nameSpace = interpreter.getNameSpace();
        nameSpace.importPackage("com.duckdeveloper.lucy");
        nameSpace.importPackage("java.util");
        nameSpace.importPackage("java.net");
        nameSpace.importPackage("net.dv8tion.jda");

        nameSpace.setVariable("user", user, false);
        nameSpace.setVariable("channel", channel, false);
        nameSpace.setVariable("guild", guild, false);
        nameSpace.setVariable("output", interpreter, false);
        Object object = interpreter.eval(code);

        if (object == null && interpreter.get("retorno") != null)
            object = interpreter.get("retorno");

        var result = object != null ? object.toString() : "null";
        return BotUtils.getMessagesStripped(result);
    }

    public static List<String> bash(String command) throws IOException {
        var process = RUNTIME.exec(command);
        var message = ObjectUtils.formatInputStream(process.getInputStream());
        return BotUtils.getMessagesStripped(message);
    }
}