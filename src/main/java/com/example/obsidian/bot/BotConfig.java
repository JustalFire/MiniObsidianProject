package com.example.obsidian.bot;

import com.example.obsidian.config.AppProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
public class BotConfig {

    @Bean
    public TelegramClient telegramClient(AppProperties properties) {
        return new OkHttpTelegramClient(properties.getBotToken());
    }
}
