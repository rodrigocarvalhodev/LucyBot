package com.duckdeveloper.lucy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties
@EnableFeignClients
public class LucyBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(LucyBotApplication.class, args);
    }
}
