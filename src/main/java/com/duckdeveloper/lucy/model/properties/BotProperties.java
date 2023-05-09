package com.duckdeveloper.lucy.model.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lucy")
@Getter @Setter
public class BotProperties {

    private String token;

}
