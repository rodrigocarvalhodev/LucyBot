package net.rodrigocarvalho.lucy.command.model;

@CommandHandler(name = "")
public abstract class AbstractCommand {

    public abstract void execute(CommandEvent event);

}