package net.rodrigocarvalho.lucy.command.model;

import net.dv8tion.jda.api.JDA;
import net.rodrigocarvalho.lucy.Lucy;

public abstract class AbstractCommand {

    public static JDA jda = Lucy.getJda();

    public static void setJda(JDA jda) {
        AbstractCommand.jda = jda;
    }

    public abstract void execute(CommandEvent event);

}