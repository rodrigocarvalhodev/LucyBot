package net.rodrigocarvalho.lucy.command.model;

public abstract class AbstractCommand {

    public abstract void execute(CommandEvent event);

}