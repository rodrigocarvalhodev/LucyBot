package net.rodrigocarvalho.lucy.command.model;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.*;

@Inherited
public @interface CommandHandler {

    String name();
    String[] aliases() default {};
    Permission permission() default Permission.UNKNOWN;

}