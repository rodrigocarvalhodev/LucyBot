package com.duckdeveloper.lucy.configuration;

import com.barchart.ondemand.BarchartOnDemandClient;
import com.duckdeveloper.lucy.command.CommandListener;
import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.event.EventAdapter;
import com.duckdeveloper.lucy.model.properties.BotProperties;
import com.duckdeveloper.lucy.utils.BotUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BotConfiguration {

    private JDA jda;

    private final BotProperties properties;

    private final CommandListener commandListener;
    private final EventAdapter eventAdapter;

    private final List<AbstractCommand> commands;

    @Bean
    public JDA initializeBot() {
        if (jda != null)
            return jda;

        this.jda = JDABuilder.createDefault(properties.getToken())
                .setAutoReconnect(true)
                .addEventListeners(commandListener, eventAdapter)
                .build();

        this.commands.forEach(command -> this.jda.upsertCommand(command.getCommandSlash()).queue());
        BotUtils.setStartTime(System.currentTimeMillis());
        System.setProperty("file.encoding", "UTF-8");
        return this.jda;
    }

    @Bean
    public Runtime getRuntime() {
        return Runtime.getRuntime();
    }

    @Bean
    public BarchartOnDemandClient barchartOnDemandClient() {
        return new BarchartOnDemandClient.Builder()
                .apiKey("CHANGE-ME")
                .build();
    }

//    @PostConstruct
//    @Bean
//    public Color defaultColor() {
//        return Color.decode("#e5e4b8");
//    }
}
