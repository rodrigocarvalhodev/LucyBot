package net.rodrigocarvalho.lucy.command.root;

import lombok.var;
import net.dv8tion.jda.api.EmbedBuilder;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.type.CommandType;
import net.rodrigocarvalho.lucy.utils.ObjectUtils;
import net.rodrigocarvalho.lucy.utils.SystemUtils;

@CommandHandler(name = "eval", rootCommand = true)
public class EvalCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        var user = event.getUser();
        var channel = event.getChannel();
        var args = event.getArgs();
        if (ObjectUtils.asArguments(CommandType.EVAL, user, channel, args)) {
            String code = ObjectUtils.formatArguments(args, 0);
            try {
                long millis = System.currentTimeMillis();
                var result = SystemUtils.eval(code, user,
                        event.getMessage(), channel, event.getGuild(), args);
                long ms = System.currentTimeMillis() - millis;
                if (result.size() > 1) {
                    event.sendMessage("Oxi, que mensagem gigante ein? <:thinkrage:563142958938062849> Irei corta-la.");
                }

                var page = 0;
                for (var content : result) {
                    page++;
                    event.sendMessage(
                            new EmbedBuilder()
                                    .setTitle("Terminamos.")
                                    .addField("Resultado", content, false)
                                    .addField("Tempo de execução", String.valueOf(ms), false)
                                    .setFooter("Página " + page, user.getAvatarUrl())
                            .build()
                    );
                }
            } catch (Exception e) {
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
