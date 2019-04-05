package net.rodrigocarvalho.lucy.command.root;

import lombok.var;
import net.rodrigocarvalho.lucy.Lucy;
import net.rodrigocarvalho.lucy.command.Command;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.type.CommandType;
import net.rodrigocarvalho.lucy.utils.FileUtils;
import net.rodrigocarvalho.lucy.utils.ObjectUtils;

@CommandHandler(name = "unloadcommand", aliases = {"disablecommand"}, rootCommand = true)
public class UnloadCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        var user = event.getUser();
        var channel = event.getChannel();
        var args = event.getArgs();
        if (ObjectUtils.asArguments(CommandType.UNLOAD_COMMAND, user, channel, args)) {
            Command command = Lucy.getCommand();
            String name = args[0];
            if (command.getCommand("." + name) == null) {
                event.sendMessage("Comando não encontrado.");
                return;
            }
            command.removeCommand(args[0]);
            event.sendMessage("Comando removido com sucesso.");
        }
    }
}
