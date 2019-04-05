package net.rodrigocarvalho.lucy.command.root;

import lombok.var;
import net.rodrigocarvalho.lucy.Lucy;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.type.CommandType;
import net.rodrigocarvalho.lucy.utils.BotUtils;
import net.rodrigocarvalho.lucy.utils.FileUtils;
import net.rodrigocarvalho.lucy.utils.ObjectUtils;

@CommandHandler(name = "loadcommand", aliases = {"enablecommand"}, rootCommand = true)
public class LoadCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        var user = event.getUser();
        var channel = event.getChannel();
        var args = event.getArgs();
        if (ObjectUtils.asArguments(CommandType.LOAD_COMMAND, user, channel, args)) {
            String file = "commands/" + args[0];
            String className = args[1];
            if (!FileUtils.exists(file)) {
                event.sendMessage("Esta classe não existe");
                return;
            }
            try {
                var command = BotUtils.getClass(file, className);
                if (command != null) {
                    Lucy.getCommand().addCommand(command);
                    event.sendMessage("Comando registrado com sucesso.");
                } else {
                    event.sendMessage("A classe citada não implementa AbstractCommand");
                }
            } catch (Exception e) {
                event.sendMessage("Ocorreu um erro, olhe o console.");
                e.printStackTrace();
            }
        }
    }
}