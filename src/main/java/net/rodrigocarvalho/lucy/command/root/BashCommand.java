package net.rodrigocarvalho.lucy.command.root;

import lombok.var;
import net.dv8tion.jda.api.EmbedBuilder;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.type.CommandType;
import net.rodrigocarvalho.lucy.utils.ObjectUtils;
import net.rodrigocarvalho.lucy.utils.SystemUtils;

import java.io.IOException;

@CommandHandler(name = "bash", rootCommand = true)
public class BashCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        var user = event.getUser();
        var args = event.getArgs();
        var channel = event.getUsedChannel();
        if (ObjectUtils.asArguments(CommandType.BASH, user, channel, args)) {
            String command = ObjectUtils.formatArguments(args, 0);
            try {
                var bash = SystemUtils.bash(command);
                if (bash.size() > 1) {
                    event.sendMessage("Oxi, que mensagem gigante ein? <:thinkrage:563142958938062849> Irei corta-la.");
                }

                var i = 0;
                for (var content : bash) {
                    i++;
                    event.sendMessage(
                            new EmbedBuilder()
                                .setTitle("Bash")
                                .setDescription("Resultado: \n\n" + content)
                                .setFooter("Página " + i, user.getAvatarUrl())
                            .build()
                    );
                }
            } catch (IOException e) {
                StackTraceElement element = e.getStackTrace()[0];
                event.sendMessage(
                        new EmbedBuilder()
                                .setTitle("Opa! parece que algo deu errado <:desesperado:563147487691407375>")
                                .addField("Erro", e.getMessage(), false)
                                .addField("Linha", element.getClassName() + ":" + element.getLineNumber(), false)
                                .build()
                );
            }
        }
    }
}
