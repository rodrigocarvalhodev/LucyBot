package com.duckdeveloper.lucy.service;

import bsh.Interpreter;
import bsh.NameSpace;
import com.duckdeveloper.lucy.reader.StreamReader;
import com.duckdeveloper.lucy.utils.BotUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BashService {

    private final Runtime runtime;
    private final StreamReader<InputStream, String> bashReader;

    @SneakyThrows
    public List<String> eval(String code, User user, MessageChannel channel, Guild guild) {
        var interpreter = new Interpreter();
        interpreter.setNameSpace(NameSpace.JAVACODE);
        var nameSpace = interpreter.getNameSpace();
        importPackagesAndVariables(nameSpace, interpreter, user, channel, guild);
        var result = interpreter.eval(code);

        if (result == null && interpreter.get("retorno") != null)
            result = interpreter.get("retorno");

        return result != null ? BotUtils.getMessagesStripped(result.toString()) : Collections.singletonList("null");
    }

    @SneakyThrows
    private void importPackagesAndVariables(NameSpace nameSpace, Interpreter interpreter, User user, MessageChannel channel, Guild guild) {
        nameSpace.importPackage("com.duckdeveloper.lucy");
        nameSpace.importPackage("java.util");
        nameSpace.importPackage("java.net");
        nameSpace.importPackage("net.dv8tion.jda");

        nameSpace.setVariable("user", user, false);
        nameSpace.setVariable("channel", channel, false);
        nameSpace.setVariable("guild", guild, false);
        nameSpace.setVariable("output", interpreter, false);
    }

    public List<String> executeBash(String command) throws IOException {
        var process = this.runtime.exec(command);
        var message = this.bashReader.read(process.getInputStream());
        return BotUtils.getMessagesStripped(message);
    }
}